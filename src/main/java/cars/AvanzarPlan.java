package cars;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;

@Plan
public class AvanzarPlan{
  private SimpleCarBDI auto;
  private int vel;

  public AvanzarPlan(SimpleCarBDI sc, int v){
    auto= sc;
    vel= v;
  }
 
  @PlanBody
  public void translateEnglishGerman()
  {
    auto.setVelocidad(vel);
  }
}