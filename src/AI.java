import java.util.ArrayList;
import java.util.Random;

public class AI{

    int levelAI;

    public int turn() {
        int position = 1;
        switch (this.levelAI) {
            case 0 -> {
                Random rand = new Random();
                return rand.nextInt(9) + 1;
            }
        }

        return position;
    }


}