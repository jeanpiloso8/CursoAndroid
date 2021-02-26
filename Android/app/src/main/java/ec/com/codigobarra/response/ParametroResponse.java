package ec.com.codigobarra.response;

import java.util.List;

public class ParametroResponse {
    private List<ParmInicial> paramIniciales;
    private String usuarioVendedor;


    public class ParmInicial {
        private String idProducto;
        private String DescripcionProducto;

        public String getIdProducto() {
            return idProducto;
        }

        public void setIdProducto(String idProducto) {
            this.idProducto = idProducto;
        }

        public String getDescripcionProducto() {
            return DescripcionProducto;
        }

        public void setDescripcionProducto(String descripcionProducto) {
            DescripcionProducto = descripcionProducto;
        }
    }

    public List<ParmInicial> getParamIniciales() {
        return paramIniciales;
    }

    public void setParamIniciales(List<ParmInicial> paramIniciales) {
        this.paramIniciales = paramIniciales;
    }

    public String getUsuarioVendedor() {
        return usuarioVendedor;
    }

    public void setUsuarioVendedor(String usuarioVendedor) {
        this.usuarioVendedor = usuarioVendedor;
    }
}
