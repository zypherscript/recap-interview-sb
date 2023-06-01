package com.example.itvqs;

import com.example.itvqs.config.TestComponent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

@SpringBootTest
@ExtendWith(OutputCaptureExtension.class)
class ItvQsApplicationTests {

  @Autowired
  @Qualifier("testComponent1")
  private TestComponent testComponent;

  @Test
  void contextLoads(CapturedOutput output) {
    testComponent.test();
    Assertions.assertTrue(output.getOut().contains("Calling Test Method 1"));
  }

}
