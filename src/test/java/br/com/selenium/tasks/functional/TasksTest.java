package br.com.selenium.tasks.functional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class TasksTest {

    private static final String ADD_TODO_BUTTON = "addTodo";
    private static final String TASK_FIELD = "task";
    private static final String DUE_DATE_FIELD = "dueDate";
    private static final String SAVE_BUTTON = "saveButton";
    private static final String MESSAGE_LABEL = "message";

    @Test
    public void shouldSaveTaskSuccessfully() {
        WebDriver webDriver = accessApp();
        try {
            webDriver.findElement(By.id(ADD_TODO_BUTTON)).click();

            webDriver.findElement(By.id(TASK_FIELD)).sendKeys("Study Selenium");
            webDriver.findElement(By.id(DUE_DATE_FIELD)).sendKeys("26/04/2020");
            webDriver.findElement(By.id(SAVE_BUTTON)).click();

            String message = webDriver.findElement(By.id(MESSAGE_LABEL)).getText();
            Assert.assertEquals("Task add successfully!", message);
        } finally {
            webDriver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskNoDescription() {
        WebDriver webDriver = accessApp();
        try {
            webDriver.findElement(By.id(ADD_TODO_BUTTON)).click();

            webDriver.findElement(By.id(DUE_DATE_FIELD)).sendKeys("26/04/2020");
            webDriver.findElement(By.id(SAVE_BUTTON)).click();

            String message = webDriver.findElement(By.id(MESSAGE_LABEL)).getText();
            Assert.assertEquals("Fill the task description", message);
        } finally {
            webDriver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskNoDate() {
        WebDriver webDriver = accessApp();
        try {
            webDriver.findElement(By.id(ADD_TODO_BUTTON)).click();

            webDriver.findElement(By.id(TASK_FIELD)).sendKeys("Study Selenium");
            webDriver.findElement(By.id(SAVE_BUTTON)).click();

            String message = webDriver.findElement(By.id(MESSAGE_LABEL)).getText();
            Assert.assertEquals("Fill the due date", message);
        } finally {
            webDriver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskWithPastDate() {
        WebDriver webDriver = accessApp();
        try {
            webDriver.findElement(By.id(ADD_TODO_BUTTON)).click();

            webDriver.findElement(By.id(TASK_FIELD)).sendKeys("Study Selenium");
            webDriver.findElement(By.id(DUE_DATE_FIELD)).sendKeys("20/04/2020");
            webDriver.findElement(By.id(SAVE_BUTTON)).click();

            String message = webDriver.findElement(By.id(MESSAGE_LABEL)).getText();
            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            webDriver.quit();
        }
    }

    private WebDriver accessApp() {
        WebDriver webDriver = new ChromeDriver();
        webDriver.navigate().to("http://localhost:8001/tasks");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return webDriver;
    }

}
