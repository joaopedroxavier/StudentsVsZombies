package StudentsVsZombies;

import java.awt.*;

public class Card extends GameObject{
   private Breed breed;

   public Card(Breed breed, GameObject obj) {
      super(obj, new Point(obj.x_, obj.y_));
      this.breed = breed;
   }

   public Breed getBreed() { return breed; }
}