package cars;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;

@Plan
public class GirarDerechaPlan{
  private SimpleCarBDI auto;
  private int dirActual;

  public GirarDerechaPlan(SimpleCarBDI sc){
    auto= sc;
    dirActual= auto.getDireccion();
  }
 
  @PlanBody
  public void translateEnglishGerman()
  {
    int nDir= (dirActual+1)%4;
    auto.setDireccion(nDir);
  }
}