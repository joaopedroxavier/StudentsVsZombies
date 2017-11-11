package StudentsVsZombies;

import java.awt.*;

public class Card extends GameObject{
   private Breed breed;
   private int cost;

   public Card(Breed breed, GameObject obj, int cost) {
      super(obj, new Point(obj.x_, obj.y_));
      this.breed = breed; this.cost = cost;
   }

   public Breed getBreed() { return breed; }
   public int getCost() { return cost; }

   public Card copy() {
      return new Card(breed, new GameObject(this, new Point(this.x_, this.y_)), cost);
   }
}