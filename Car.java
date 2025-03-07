
package za.ac.cput.domain;

import java.io.Serializable;

/**
 *
 * @author 27604
 */
public class Car implements Serializable{
   private int carId;
   private String carName;
   private int carVote;
   
   public Car(){
       
   }
    public Car(String carName){
        this.carName = carName;
    }
    
    public Car(int carId, String carName){
        this.carId = carId;
        this.carName = carName;
    }
   
   
   public Car(int carId, String carName, int carVote){
       this.carId = carId;
       this.carName = carName;
       this.carVote = carVote;
   }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getCarVote() {
        return carVote;
    }

    public void setCarVote(int carVote) {
        this.carVote = carVote;
    }

    @Override
    public String toString() {
        return "Car{" + "Car Id: " + carId + "\n" + "Car Name: " + carName + "\n" + "Car Vote: " + carVote + '}';
        
    }
    
}//end class
