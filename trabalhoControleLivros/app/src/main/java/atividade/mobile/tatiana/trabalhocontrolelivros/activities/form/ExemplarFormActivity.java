package atividade.mobile.tatiana.trabalhocontrolelivros.activities.form;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseBook;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseExemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseHelper;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabasePublisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.Database.DatabaseStatus;
import atividade.mobile.tatiana.trabalhocontrolelivros.Enums.BookType;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Book;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Exemplar;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Publisher;
import atividade.mobile.tatiana.trabalhocontrolelivros.R;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.Status;
import atividade.mobile.tatiana.trabalhocontrolelivros.Models.User;

public class ExemplarFormActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private User user;
    private Exemplar exemplar;
    private boolean newExemplar;

    private static final String TAG = "FormularioExemplar";

    private LinearLayout linLayID; // Visibility - Id
    private TextView edtId; // Id
    // Required
    private Spinner spnBook; // Livro
    private Spinner edtStatus; // Status
    private Spinner spnEditor; // Editora
    private EditText edtEdition; // Edição
    private EditText edtPages; // Páginas
    private RadioGroup rdgBookType; // Tipo de livro
    private Spinner spnLanguage; // Língua
    // Others
    private LinearLayout linLayOpt; // Visibility - Other options
    private EditText edtTimesRead; // Vezes lido
    private EditText edtTimesLent; // Vezes emprestado
    private EditText edtCoverImage; // URL da imagem da capa
    private EditText edtComments; // comentários
    private EditText edtClassificationLast; // última classificação
    private TextView edtClassificationMean; // Média das classificações
    private TextView edtTimesClassificated; // Quantidade de vezes q foi classificado

    private Book book;
    private Status status;
    private Publisher publisher;
    private String language;

    private float prevClassLast;
    private DatabaseExemplar exemplarDb;
//    private DatabaseHelper helper;
    // R.id.radioButton = eBook, R.id.radioButton2 = livro físico
    // int selectedId = radioGroup.getCheckedRadioButtonId();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exemplar_form);

        user = (User) getIntent().getExtras().getSerializable("logged_user");

        if(user == null){
            finish();
        }
        exemplar = (Exemplar) getIntent().getExtras().getSerializable("Exemplar");


        linLayID = findViewById(R.id.linLayExemplarFormID);
        edtId = findViewById(R.id.edtIdExemplarForm);
        spnBook = findViewById(R.id.spnBookExemplarForm);
        edtStatus = findViewById(R.id.edtStatusExemplarForm);
        spnEditor = findViewById(R.id.spnEditorExemplarForm);
        edtEdition = findViewById(R.id.edtEditionExemplarForm);
        edtPages = findViewById(R.id.edtPagesExemplarForm);
        rdgBookType = findViewById(R.id.rdgBookTypeExemplarForm);
        spnLanguage = findViewById(R.id.spnLanguageExemplarForm);
        linLayOpt = findViewById(R.id.linLayExemplarFormOpt);
        edtTimesRead = findViewById(R.id.edtTimesReadExemplarForm);
        edtTimesLent = findViewById(R.id.edtTimesLentExemplarForm);
        edtCoverImage = findViewById(R.id.edtCoverImageExemplarForm);
        edtComments = findViewById(R.id.edtCommentsExemplarForm);
        edtClassificationLast = findViewById(R.id.edtClassificationLastExemplarForm);
        edtClassificationMean = findViewById(R.id.edtClassificationMeanExemplarForm);
        edtTimesClassificated = findViewById(R.id.edtTimesClassificatedExemplarForm);

//        helper = DatabaseHelper.getInstance(this);
        DatabaseStatus statusDb = new DatabaseStatus(this);
        List<Status> statuses = statusDb.getAllStatus();

        exemplarDb = new DatabaseExemplar(this);

        DatabaseBook bookDb = new DatabaseBook(this);
        List<Book> books = bookDb.getAllBooks();

        DatabasePublisher publisherDb = new DatabasePublisher(this);

        List<Publisher> publishers = publisherDb.getAllPublishers();

        ArrayAdapter<Status> adapterStatus = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statuses);
        adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtStatus.setAdapter(adapterStatus);
        edtStatus.setOnItemSelectedListener(this);

        ArrayAdapter<Book> adapterBook = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, books);
        adapterBook.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnBook.setAdapter(adapterBook);
        spnBook.setOnItemSelectedListener(this);

        ArrayAdapter<Publisher> adapterPubs = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, publishers);
        adapterPubs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnEditor.setAdapter(adapterPubs);
        spnEditor.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> adapterLang = ArrayAdapter.createFromResource(this, R.array.languages, android.R.layout.simple_spinner_item);
        adapterLang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLanguage.setAdapter(adapterLang);
        spnLanguage.setOnItemSelectedListener(this);

        if(exemplar == null){
            newExemplar = true;
            linLayID.setVisibility(View.GONE);
            linLayOpt.setVisibility(View.GONE);
        }
        else{
            newExemplar = false;
            linLayID.setVisibility(View.VISIBLE);
            linLayOpt.setVisibility(View.VISIBLE);

            prevClassLast = exemplar.getClassificationLast();

            if (exemplar.getBookType() == BookType.EBOOK) {
                rdgBookType.check(R.id.radioButton);
            }
            else{
                rdgBookType.check(R.id.radioButton2);
            }

            if (exemplar.getBook() != null) {
//                Log.i(TAG, "Livro: " + exemplar.getBook().getTitle());
                book = exemplar.getBook();
                int spinnerPosition = adapterBook.getPosition(exemplar.getBook());
                spnBook.setSelection(spinnerPosition);
            }
            if (exemplar.getStatus() != null) {
                int spinnerPosition = adapterStatus.getPosition(exemplar.getStatus());
                edtStatus.setSelection(spinnerPosition);
            }
            if (exemplar.getPublisher() != null) {
                int spinnerPosition = adapterPubs.getPosition(exemplar.getPublisher());
                spnEditor.setSelection(spinnerPosition);
            }
            if (exemplar.getLanguage() != null) {
                int spinnerPosition = adapterLang.getPosition(exemplar.getLanguage());
                spnLanguage.setSelection(spinnerPosition);
            }

            edtId.setText(String.valueOf(exemplar.getId()));
            edtEdition.setText(String.valueOf(exemplar.getEdition()));
            edtPages.setText(String.valueOf(exemplar.getPages()));
            edtTimesRead.setText(String.valueOf(exemplar.getTimesRead()));
            edtTimesLent.setText(String.valueOf(exemplar.getTimesLent()));
            edtCoverImage.setText(exemplar.getCoverImage());
            edtComments.setText(exemplar.getComments());
            edtClassificationLast.setText(String.valueOf(exemplar.getClassificationLast()));
            edtClassificationMean.setText(String.valueOf(exemplar.getClassificationMean()));
            edtTimesClassificated.setText(String.valueOf(exemplar.getTimesClassificated()));
        }
    }

    private void saveExemplar(){
//        DatabaseHelper helper = DatabaseHelper.getInstance(this);

        BookType bookType;
        int selectedId = rdgBookType.getCheckedRadioButtonId();
        if (selectedId == R.id.radioButton) {
            bookType = BookType.EBOOK;
        }
        else{
            bookType = BookType.PHYSICAL_COPY;
        }

        int edition = Integer.parseInt(edtEdition.getText().toString());
        int pages = Integer.parseInt(edtPages.getText().toString());


        if(newExemplar){
//            Log.i(TAG, "Livro selecionado: " + book.getTitle());
            exemplar = new Exemplar(book, user, status, publisher, edition, pages, bookType, language);
            exemplarDb.insertExemplar(exemplar);
        }
        else{
            int timesRead = Integer.parseInt(edtTimesRead.getText().toString());
            int timesLent = Integer.parseInt(edtTimesLent.getText().toString());

//            Log.i(TAG, "Editora selecionada: " + publisher.getName());

            exemplar.setBook(book);
            exemplar.setStatus(status);
            exemplar.setPublisher(publisher);
            exemplar.setLanguage(language);
            exemplar.setBookType(bookType);

            exemplar.setEdition(edition);
            exemplar.setPages(pages);
            String cover = edtCoverImage.getText().toString();
            String comments = edtComments.getText().toString();
            exemplar.setTimesRead(timesRead);
            exemplar.setTimesLent(timesLent);

            exemplar.setCoverImage(cover);
            exemplar.setComments(comments);

            float classLast = Float.parseFloat(edtClassificationLast.getText().toString());

            if (prevClassLast != classLast || exemplar.getTimesClassificated() > 0){
                exemplar.setTimesClassificated(exemplar.getTimesClassificated() + 1);
                exemplar.setClassificationLast(classLast);
            }
            exemplarDb.updateExemplar(exemplar);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.form_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_cancel) {
            finish();
            return true;
        }
        else if (id == R.id.action_save) {
            saveExemplar();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        switch(parent.getId()){
            case R.id.spnBookExemplarForm:
                book = (Book) parent.getItemAtPosition(pos);
//                Log.i(TAG, "Setando livro "+ book.getTitle());
                break;
            case R.id.edtStatusExemplarForm:
                status = (Status) parent.getItemAtPosition(pos);
                break;
            case R.id.spnEditorExemplarForm:
                publisher = (Publisher) parent.getItemAtPosition(pos);
                break;
            case R.id.spnLanguageExemplarForm:
                language = (String) parent.getItemAtPosition(pos);
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        // status = exemplar.getStatus();
        // changed = false;
        switch(parent.getId()){
            case R.id.spnBookExemplarForm:
                if (newExemplar){
                    book = (Book) parent.getItemAtPosition(0);
                }
                else{
                    book = exemplar.getBook();
                }
                break;
            case R.id.edtStatusExemplarForm:
                if (newExemplar){
                    status = (Status)parent.getItemAtPosition(0);
                }
                else{
                    status = exemplar.getStatus();
                }
                break;
            case R.id.spnEditorExemplarForm:
                if (newExemplar){
                    publisher = (Publisher)parent.getItemAtPosition(0);
                }
                else{
                    publisher = exemplar.getPublisher();
                }
                break;
            case R.id.spnLanguageExemplarForm:
                if(newExemplar){
                    language = (String)parent.getItemAtPosition(0);
                }
                else{
                    language = exemplar.getLanguage();
                }
                break;
        }
    }
}