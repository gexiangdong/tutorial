package cn.devmgr.tutorial.tx.test;

import cn.devmgr.tutorial.tx.HeroDto;
import cn.devmgr.tutorial.tx.PersonDto;
import cn.devmgr.tutorial.tx.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AppTests {
  private static final Logger logger = LoggerFactory.getLogger(AppTests.class);

  @Autowired PersonService serv;

  @Test
  public void testSomething() {
    List<PersonDto> persons = serv.queryPersons();
    List<HeroDto> heroes = serv.queryHeros();
    for (PersonDto person : persons) {
      logger.trace("person: {}, {}, {}", person.getId(), person.getName());
    }
    for (HeroDto hero : heroes) {
      logger.trace("hero: {}, {}, {}", hero.getId(), hero.getName());
    }
  }
}
