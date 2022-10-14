package cn.devmgr.tutorial.tx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
  private static final Logger logger = LoggerFactory.getLogger(PersonService.class);

  @Autowired private PersonDao personDao;
  @Autowired private HeroDao heroDao;

  public List<PersonDto> queryPersons() {
    return personDao.getAll();
  }

  public List<HeroDto> queryHeros() {
    return heroDao.getAll();
  }
}
