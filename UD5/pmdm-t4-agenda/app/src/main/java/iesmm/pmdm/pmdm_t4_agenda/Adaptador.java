package iesmm.pmdm.pmdm_t4_agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>{
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.contacto_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.email.setText(contactoList.get(position).getEmail());
        holder.nombres.setText(contactoList.get(position).getNombre());
        holder.telefono.setText(contactoList.get(position).getTelefono());
        holder.imagen.setImageResource(contactoList.get(position).getImage());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(v.getContext(),v);
                popupMenu.getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.opcion1) {
                            realizarLlamada(holder.telefono.getText().toString());
                            return true;
                        } else if (item.getItemId() == R.id.opcion2) {
                            mandarWhatsApp();
                            return true;
                        }else {
                            return false;
                        }
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return contactoList.size();
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder{
        private TextView nombres,telefono,email;
        private ImageView imagen;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.nombres = (TextView) itemView.findViewById(R.id.nombre);
            this.telefono = (TextView) itemView.findViewById(R.id.telefono);
            this.email = (TextView) itemView.findViewById(R.id.email);
            this.imagen = (ImageView) itemView.findViewById(R.id.imagen);
            this.cardView=(CardView) itemView.findViewById(R.id.card);

        }
    }
    private Context context;
    public List<Contacto> contactoList;
    public Adaptador(List<Contacto> contactoList,Context context){
        this.contactoList=contactoList;
        this.context = context;
    }
    public void realizarLlamada(String telefono) {
        if (confirmarPermisoLlamada()){
            Intent intent=new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+telefono));
            context.startActivity(intent);
        }else {
            Toast.makeText(context,"No hay permios",Toast.LENGTH_SHORT).show();
        }

    }
    private boolean confirmarPermisoLlamada() {
        boolean confirmado = false;

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED)
        {
            Activity activity=(Activity) context;
            activity.requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, 0);
        }
        else
            confirmado = true;

        return confirmado;
    }
    public void mandarWhatsApp(){
        //Comprobar que Whatsap está instañado

        boolean instalado=whatsAppInstalado(context);

        if (instalado){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");
            sendIntent.setPackage("com.whatsapp");
            context.startActivity(sendIntent);
        }else {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.whatsapp"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
    private boolean whatsAppInstalado(Context context) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
