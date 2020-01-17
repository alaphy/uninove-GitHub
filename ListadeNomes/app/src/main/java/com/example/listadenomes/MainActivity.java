package com.example.listadenomes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.listadenomes.dao.PessoaDao;
import com.example.listadenomes.modelo.Pessoa;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listVisivel;
    Button btnNovoCadastro;
    Pessoa pessoa;
    PessoaDao pessoaDao;
    ArrayList<Pessoa> arrayListPessoa;
    ArrayAdapter<Pessoa> arrayAdapterPessoa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listVisivel = (ListView) findViewById(R.id.listPessoas);
        registerForContextMenu(listVisivel);



        btnNovoCadastro = (Button) findViewById(R.id.btnNovoCadastro);

        btnNovoCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this,FormNomes.class);
                startActivity(i);

            }
        });

        listVisivel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pessoa pessoaEnviada = (Pessoa) arrayAdapterPessoa.getItem(position);
                Intent i = new Intent(MainActivity.this, FormNomes.class);
                i.putExtra("pessoa-enviada",pessoaEnviada);

                startActivity(i);

            }
        });

      listVisivel.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
          @Override
          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
              pessoa = arrayAdapterPessoa.getItem(position);


              return false;
          }
      });


    }

    public void populaLista(){

        pessoaDao = new PessoaDao(MainActivity.this);

        arrayListPessoa = pessoaDao.selectAllPessoa();
        pessoaDao.close();

        if(listVisivel != null){

            arrayAdapterPessoa = new ArrayAdapter<>(MainActivity.this,
                    android.R.layout.simple_list_item_1, arrayListPessoa);
            listVisivel.setAdapter(arrayAdapterPessoa);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        populaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem mDelete = menu.add("Deletar Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                long retornoDB;
                pessoaDao = new PessoaDao(MainActivity.this);
                retornoDB = pessoaDao.exluirPessoa(pessoa);
                pessoaDao.close();
                if(retornoDB == -1){
                    alert("Erro de exclusão");



                }else{
                    alert("Registro Excluido com Sucesso!!!");

                }

                populaLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private  void alert(String s){

        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();

    }


}
