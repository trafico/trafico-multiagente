package cars;

import jadex.bdiv3.annotation.Plan;
import jadex.bdiv3.annotation.PlanBody;

@Plan
public class DetenersePlan{
  private SimpleCarBDI auto;

  public DetenersePlan(SimpleCarBDI sc){
    auto= sc;
  }
 
  @PlanBody
  public void translateEnglishGerman()
  {
    auto.setVelocidad(0);
  }
}