package com.ufc.taskmanager_projetofinal.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ufc.taskmanager_projetofinal.R;
import com.ufc.taskmanager_projetofinal.adapter.GrupoSelecionadoAdapter;
import com.ufc.taskmanager_projetofinal.adapter.TopicoAdapter;
import com.ufc.taskmanager_projetofinal.config.ConfiguracaoFirebase;
import com.ufc.taskmanager_projetofinal.helper.UserFirebase;
import com.ufc.taskmanager_projetofinal.model.Tarefa;
import com.ufc.taskmanager_projetofinal.model.Topico;
import com.ufc.taskmanager_projetofinal.model.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CadastroGrupoActivity extends AppCompatActivity {

    private static final int SELECAO_GALERIA = 200;
    private Toolbar toolbar;
    private ArrayList<User> listaMembrosSelecionados = new ArrayList<>();
    private ArrayList<Topico> listaTopicos = new ArrayList<>();
    TextView textTotalParticipantes;
    private RecyclerView recyclerMembrosSelecionados;
    private RecyclerView recyclerTopicos;
    private GrupoSelecionadoAdapter grupoSelecionadoAdapter;
    private TopicoAdapter topicoAdapter;
    private CircleImageView imageTarefa;
    private StorageReference storageReference;
    private Tarefa tarefa;
    private FloatingActionButton fabSalvarTarefa;
    private EditText editNomeTarefa;
    private EditText editTopico;
    private TextView textTopico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_grupo);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Nova Tarefa");
        toolbar.setSubtitle("Defina o nome");

        setSupportActionBar(toolbar);

        //configurações iniciais
        textTotalParticipantes = findViewById(R.id.textTotalParticipantes);
        recyclerMembrosSelecionados = findViewById(R.id.recyclerMembrosGrupo);
        recyclerTopicos = findViewById(R.id.recyclerTopicos);
        imageTarefa = findViewById(R.id.imageTarefa);
        fabSalvarTarefa = findViewById(R.id.fabSalvarTarefa);
        editNomeTarefa = findViewById(R.id.editNomeTarefa);
        editTopico = findViewById(R.id.editTextTopico);
        textTopico = findViewById(R.id.textTopico);

        storageReference = ConfiguracaoFirebase.getFirebaseStorage();
        tarefa = new Tarefa();

        //configurar evento de clique
        imageTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                if(i.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(i, SELECAO_GALERIA);
                }
            }
        });

        //configurar recyclerview topicos
        topicoAdapter = new TopicoAdapter(listaTopicos, getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());

        recyclerTopicos.setLayoutManager(layoutManager);
        recyclerTopicos.setHasFixedSize(true);
        recyclerTopicos.setAdapter(topicoAdapter);

        topicoAdapter.notifyDataSetChanged();


        //recuperar lista de membros passada
        if(getIntent().getExtras() != null){
            List<User> membros = (List<User>) getIntent().getExtras().getSerializable("membros");
            listaMembrosSelecionados.addAll(membros);
            textTotalParticipantes.setText("Participantes: " + listaMembrosSelecionados.size());
        }

        //configurar recyclerview
        grupoSelecionadoAdapter = new GrupoSelecionadoAdapter(listaMembrosSelecionados, getApplicationContext());

        RecyclerView.LayoutManager layoutManagerHorizontal = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );

        recyclerMembrosSelecionados.setLayoutManager(layoutManagerHorizontal);
        recyclerMembrosSelecionados.setHasFixedSize(true);
        recyclerMembrosSelecionados.setAdapter(grupoSelecionadoAdapter);

        //configurar floatingActionButton
        fabSalvarTarefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeTarefa = editNomeTarefa.getText().toString();

                //adiciona a lista de membros o usuário logado
                listaMembrosSelecionados.add(UserFirebase.getDadosUserLogado());
                tarefa.setMembros(listaMembrosSelecionados);
                tarefa.setNome(nomeTarefa);
                tarefa.setTopicos(listaTopicos);
                tarefa.salvar();
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            Bitmap imagem = null;

            try {

                Uri localimagem = data.getData();
                imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localimagem);

                if(imagem != null){
                    imageTarefa.setImageBitmap(imagem);

                    //recuperar dados da imagem para o firebase
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imagem.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] dadosImagem = baos.toByteArray();

                    //Salvar imagem no firebase
                    final StorageReference imagemRef = storageReference
                            .child("imagens")
                            .child("grupos")
                            .child(tarefa.getId() + ".jpeg");

                    UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CadastroGrupoActivity.this,
                                    "Erro ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(CadastroGrupoActivity.this,
                                    "Sucesso ao fazer upload da imagem",
                                    Toast.LENGTH_SHORT).show();
                            imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    String url = task.getResult().toString();
                                    tarefa.setFoto(url);
                                }
                            });
                        }
                    });

                }

            } catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    public void adcionarTopico(View view){
        Topico topico = new Topico();

        topico.setTitulo(editTopico.getText().toString());

        listaTopicos.add(topico);

        topicoAdapter.notifyDataSetChanged();

    }

}
