package iesmm.pmdm.pmdm_t5_sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iesmm.pmdm.pmdm_t5_sqlite.dao.DAO;
import iesmm.pmdm.pmdm_t5_sqlite.dao.DAOImpl;

public class MainActivity extends AppCompatActivity {
    private DAOImpl myDb;
    private DAO dao;
    private EditText name, surname, marks, ID;
    private Button add, view, update, delete, view_Marks, deleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new DAOImpl(this);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        marks = (EditText) findViewById(R.id.marks);
        ID = (EditText) findViewById(R.id.id);
        add = (Button) findViewById(R.id.add);
        view = (Button) findViewById(R.id.view);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        view_Marks=(Button) findViewById(R.id.view_marks);
        deleteAll=(Button) findViewById(R.id.deleteall);

        addData();
        updateData();
        deleteData();
        viewData();
        gatMarks();
        deleteAllboton();
    }

    public void addData() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = dao.insert(name.getText().toString(), surname.getText().toString(), marks.getText().toString());
                if (isInserted) {
                    Toast.makeText(MainActivity.this, "Datos insertados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al insertar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateData() {
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isUpdated = dao.update(ID.getText().toString(), name.getText().toString(), surname.getText().toString(), marks.getText().toString());
                if (isUpdated == true) {
                    Toast.makeText(MainActivity.this, "Datos actualizados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al actualizar", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void deleteData() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int deletedRows = dao.delete(ID.getText().toString());
                if (deletedRows > 0) {
                    Toast.makeText(MainActivity.this, "Datos eliminados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al eliminar", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void viewData() {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = dao.getAllData();
                if (res.getCount() > 0) {
                    StringBuffer buffer = new StringBuffer();

                    while (res.moveToNext()) {
                        buffer.append("ID :" + res.getString(0) + "\n");
                        buffer.append("Name :" + res.getString(1) + "\n");
                        buffer.append("Surname :" + res.getString(2) + "\n");
                        buffer.append("Marks :" + res.getString(3) + "\n\n");
                    }

                    showMessage("Datos", buffer.toString());
                } else
                    showMessage("Error", "Ningún dato encontrado");
            }
        });
    }
    public void gatMarks() {
        view_Marks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = dao.gatMarks(marks.getText().toString());
                if (res.getCount() > 0) {
                    StringBuffer buffer = new StringBuffer();

                    while (res.moveToNext()) {
                        buffer.append("ID :" + res.getString(0) + "\n");
                        buffer.append("Name :" + res.getString(1) + "\n");
                        buffer.append("Surname :" + res.getString(2) + "\n");
                        buffer.append("Marks :" + res.getString(3) + "\n\n");
                    }
                    showMessage("Datos", buffer.toString());
                } else
                    showMessage("Error", "Ningún dato encontrado");
            }
        });
    }
    public void deleteAllboton() {
        deleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean deletedRows = dao.borrarTodo();
                if (!deletedRows) {
                    Toast.makeText(MainActivity.this, "Datos eliminados", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error al eliminar", Toast.LENGTH_LONG).show();
                }

            }
        });
    }




    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}