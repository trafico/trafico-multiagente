package cars;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;

@Plan
public class GirarIzquierdaPlan{
  private SimpleCarBDI auto;
  private int dirActual;

  public GirarIzquierdaPlan(SimpleCarBDI sc){
    auto= sc;
    dirActual= auto.getDireccion();
  }
 
  @PlanBody
  public void translateEnglishGerman()
  {
    int nDir= ((dirActual+4)-1)%4;
    auto.setDireccion(nDir);
  }
}