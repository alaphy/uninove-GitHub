package com.example.listadenomes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listadenomes.dao.PessoaDao;
import com.example.listadenomes.modelo.Pessoa;

public class FormNomes extends AppCompatActivity {

    EditText editNome;
    Button btnVariavel;

    Pessoa pessoa,altpessoa;
    PessoaDao pessoaDao;
    long retornoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_nomes);

        Intent i = getIntent();
        altpessoa = (Pessoa) i.getSerializableExtra("pessoa-enviada");
        pessoa = new Pessoa();
        pessoaDao = new PessoaDao(FormNomes.this);

        editNome = (EditText) findViewById(R.id.editNome);
        btnVariavel = (Button) findViewById(R.id.btnVariavel);

        if(altpessoa != null){
            btnVariavel.setText("Alterar");
            editNome.setText(altpessoa.getNome());

            pessoa.setId(altpessoa.getId());

        }else{
            btnVariavel.setText("Salvar");
        }

        btnVariavel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pessoa.setNome(editNome.getText().toString());

                if(btnVariavel.getText().toString().equals("Salvar")){

                    retornoDB = pessoaDao.salvarPessoa(pessoa);
                    pessoaDao.close();
                    if(retornoDB == -1){

                        alert("Erro ao Cadastrar");

                    }else{

                        alert("Cadastro Realizado");
                    }



                }else{

                    retornoDB = pessoaDao.alterarPessoa(pessoa);
                    pessoaDao.close();
                    if (retornoDB==-1){
                        alert("Erro ao Alterar");

                    }else{
                        alert("Atualizado com Sucesso!!!");
                    }


                }

                finish();
            }

        });


    }

    private void alert (String s){
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

}
