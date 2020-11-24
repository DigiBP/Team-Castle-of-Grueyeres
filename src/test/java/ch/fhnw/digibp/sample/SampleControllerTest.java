package ch.fhnw.digibp.sample;

import java.util.Optional;

import ch.fhnw.digibp.AbstractSeleniumTest;
import ch.fhnw.digibp.TestDataFactory;
import ch.fhnw.digibp.domain.SampleType;
import ch.fhnw.digibp.order.Order;
import ch.fhnw.digibp.order.OrderRepository;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.task.TaskQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SampleControllerTest extends AbstractSeleniumTest {

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private TaskService taskService;

    @BeforeEach
    public void before() {
        when(orderRepository.save(any(Order.class))).thenAnswer(i -> i.getArgument(0, Order.class));
        when(orderRepository.findById(anyString())).thenReturn(Optional.of(TestDataFactory.buildTestOrder()));
        TaskQuery taskQuery = mock(TaskQuery.class);
        Task task = mock(Task.class);
        when(taskQuery.singleResult()).thenReturn(task);
        when(taskService.createTaskQuery()).thenReturn(taskQuery);
    }

    @Test
    public void test() {
        openUrl("order/" + TestDataFactory.ORDER_UUID + "/sample");
        findElementByCss("#temperatureIndicatorOk").click();

        WebElement amountUnit = findElementByCss("#sampleType");
        new Select(amountUnit).selectByVisibleText("SALIVA");
        findElementByCss("button[value=\"save\"]").click();


        verify(orderRepository, times(2)).findById(anyString());
        verify(orderRepository, times(1)).save(argThat(this::assertOrder));
    }

    private boolean assertOrder(Order order) {
        return SampleType.SALIVA.equals(order.getSample().getSampleType());
    }

}