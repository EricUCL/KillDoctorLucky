package game.utils;

import static org.junit.Assert.assertEquals;

import game.controller.GameController;
import game.controller.GameControllerImpl;
import game.model.KillDoctorLucky;
import game.model.KillDoctorLuckyImpl;
import game.view.CommandLineView;
import game.view.View;
import org.junit.Test;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

public class DfsGraphImplTest {
  @Test
  public void test() throws IOException {
    Readable fileReader = new InputStreamReader(new FileInputStream("res/mansion.txt"));
    KillDoctorLucky model = new KillDoctorLuckyImpl(10, new RandomGenerator());
    Appendable output = new StringBuilder();
    View view = new CommandLineView(output);
    Readable inputReader = new StringReader("");
    GameControllerImpl gameController = new GameControllerImpl(model, inputReader, view, fileReader);
    gameController.readFile(fileReader);
    Graph dfsGraph = new DfsGraphImpl();
    dfsGraph.setStartingRoom(model.getRooms().get(0));
    StringBuilder sb = new StringBuilder();
    for(int i=0;i<21;i++){
      sb.append(dfsGraph.getNextRoom().getIndex()).append(" ");
    }
    assertEquals(sb.toString(),"0 4 19 8 14 17 18 10 13 12 11 9 16 5 15 20 2 7 6 3 1 ");
  }
}
