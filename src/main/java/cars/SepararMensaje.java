package cars;

import java.util.StringTokenizer;


//Separa una línea de texto en diferentes tokens
class SepararMensaje  {
	private String mensaje;
	private String[] resultado;
	private int nDatos;
	
    public SepararMensaje(String m){       
        mensaje= m;
        iniciarSep();
    }
    
    public int getNDatos(){
    	return nDatos;
    }
    
    public String[] getResultado(){
    	return resultado;
    }
    
    private void iniciarSep(){        
	StringTokenizer tokens=new StringTokenizer(mensaje, " ");
        nDatos=tokens.countTokens();
        String[] resultado=new String[nDatos];
        int i=0;
        while(tokens.hasMoreTokens()){
            String str=tokens.nextToken();
            resultado[i]=str;
            i++;
        }
    }
    
}