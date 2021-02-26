package ec.com.codigobarra.activity.ui.gallery;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ec.com.codigobarra.CodigoBarraAplication;
import ec.com.codigobarra.R;
import ec.com.codigobarra.fragment.DatePickerFragment;
import ec.com.codigobarra.interfaces.ICodigoBarra;
import ec.com.codigobarra.response.ParametroResponse;
import ec.com.codigobarra.response.UsuarioResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class GalleryFragment extends Fragment {


    String[] productos = {"Televisor", "MicroOnda", "Refrigeradora", "Cocina"};
    Spinner spnProductos;
    EditText edtFecha;
    EditText edtVendedor;
    Button btnGuardarProducto;
    RatingBar rtBar;
    private SimpleDateFormat dateFormat;
    View vista;
    private Call<ParametroResponse> callParametros;
    private Call<UsuarioResponse> callGuardarProducto;


    ParametroResponse parametroResponse;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vista = view;
        llenarSpinner();
        inicializarVista();
    }

    private void llenarSpinner() {
        ICodigoBarra iCodigoBarra = CodigoBarraAplication.getmInstance().getRestAdapter().create(ICodigoBarra.class);
        callParametros = iCodigoBarra.obtenerParametros();
        callParametros.enqueue(new Callback<ParametroResponse>() {
            @Override
            public void onResponse(Call<ParametroResponse> call, Response<ParametroResponse> response) {
                if(response.isSuccessful()){
                    parametroResponse = response.body();
                    if(parametroResponse != null){
                        seteaValoresSpinner(parametroResponse.getParamIniciales());
                        setearCajaTexto(parametroResponse.getUsuarioVendedor());
                    }
                }
            }

            @Override
            public void onFailure(Call<ParametroResponse> call, Throwable t) {

            }
        });
    }

    private void setearCajaTexto(String usuarioVendedor) {
        edtVendedor.setText(usuarioVendedor);
    }

    private void seteaValoresSpinner(List<ParametroResponse.ParmInicial> parametroResponse) {
        List<String> lstProducto = new ArrayList<String>();
        for (ParametroResponse.ParmInicial productosBd : parametroResponse){
            lstProducto.add(productosBd.getDescripcionProducto());
        }
        ArrayAdapter adapterSpinner = new ArrayAdapter(getContext(),
                android.R.layout.simple_spinner_item,
                lstProducto);
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnProductos.setAdapter(adapterSpinner);
    }

    private void inicializarVista() {
        spnProductos = vista.findViewById(R.id.spnProducto);
        edtFecha = vista.findViewById(R.id.edtFecha);
        btnGuardarProducto = vista.findViewById(R.id.btnGuardarProducto);
        edtVendedor = vista.findViewById(R.id.edtVendedor);
        rtBar = vista.findViewById(R.id.rtBar);
        dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarCalendario();
            }
        });

        btnGuardarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarProducto();
            }
        });
    }

    private void guardarProducto() {
        String descripcionProducto = spnProductos.getSelectedItem().toString();
        String fechaCompra = edtFecha.getText().toString();
        int cantidadEstrella = rtBar.getNumStars();
        String vendedor = edtVendedor.getText().toString();
        ICodigoBarra iCodigoBarra = CodigoBarraAplication.getmInstance().getRestAdapter().create(ICodigoBarra.class);
        callGuardarProducto = iCodigoBarra.ingresarProducto(descripcionProducto,fechaCompra,cantidadEstrella,vendedor);
        callGuardarProducto.enqueue(new Callback<UsuarioResponse>() {
            @Override
            public void onResponse(Call<UsuarioResponse> call, Response<UsuarioResponse> response) {
                if(response.isSuccessful()){
                    UsuarioResponse usuarioResponse = response.body();
                    respuestaFormulario(usuarioResponse);
                }
            }

            @Override
            public void onFailure(Call<UsuarioResponse> call, Throwable t) {

            }
        });
    }

    private void respuestaFormulario(UsuarioResponse usuarioResponse) {
        AlertDialog.Builder _alertar = new AlertDialog.Builder(getContext());
        _alertar.setTitle("PRODUCTO");
        _alertar.setMessage(usuarioResponse.getMensaje());
        _alertar.setPositiveButton("Ok", null);
        _alertar.show();
    }

    private void mostrarCalendario() {
        DatePickerFragment fragmentCalendario = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String fechaSeleccionada = dayOfMonth+"/"+(month+1)+"/"+year;
                edtFecha.setText(fechaSeleccionada);
            }
        });
        fragmentCalendario.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
}