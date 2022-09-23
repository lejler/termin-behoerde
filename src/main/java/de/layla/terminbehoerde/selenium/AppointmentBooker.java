package de.layla.terminbehoerde.selenium;

import de.layla.terminbehoerde.appointment.AppointmentModel;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.ArrayList;
import java.util.List;

public class AppointmentBooker {

    private final FirefoxOptions options = new FirefoxOptions();
    private final WebDriver driver;
    private final AppointmentModel appointmentModel;
    private String chosenMonth;
    private int chosenDay;

    private final ArrayList<WebElement> availableDays = new ArrayList<>();

    public AppointmentBooker(AppointmentModel appointmentModel) {
        WebDriverManager.chromedriver().setup();
        driver = new FirefoxDriver(options);
        this.appointmentModel = appointmentModel;
    }

    public void test() {
        try {
            getCalendarPage();
            List<WebElement> tables = driver.findElements(By.tagName("table"));
            ArrayList<WebElement> temp = new ArrayList<>();
            ArrayList<String> months = new ArrayList<>();

            for (WebElement table : tables) {
                temp.add(table.findElement(By.className("month")));
            }

            for (WebElement tempMonth : temp) {
                String[] split = tempMonth.getAttribute("innerHTML").split(" ");
                months.add(split[0]);
            }

            for (String month : months) {
                if (month.equalsIgnoreCase(appointmentModel.getMonth())) chosenMonth = month;
            }

        } finally {
            driver.quit();
        }
    }

    private void getCalendarPage() {
        driver.get("https://service.berlin.de/terminvereinbarung/termin/tag.php?termin=1&anliegen[]=120703&dienstleisterlist=122217,327316,122219,327312,122227,327314,122231,327346,122238,122243,327348,122252,329742,122260,329745,122262,329748,122254,331011,329751,122271,327278,122273,327274,122277,327276,330436,122280,327294,122282,327290,122284,327292,122291,327270,122285,327266,122286,327264,122296,327268,150230,329760,122301,327282,122297,327286,122294,327284,122312,329763,122314,329775,122304,327330,122311,327334,122309,327332,122281,327352,122283,122279,329772,122276,327324,122274,327326,122267,329766,122246,327318,122251,327320,327653,122257,330265,327322,122208,327298,122226,327300&herkunft=http%3A%2F%2Fservice.berlin.de%2Fdienstleistung%2F120703%2F");
    }

    public static void main(String[] args) {
        AppointmentBooker booker = new AppointmentBooker(new AppointmentModel("Friedrichshain-Kreuzberg", 1, "August"));
        booker.test();
    }

    public FirefoxOptions getOptions() {
        return options;
    }

    public WebDriver getDriver() {
        return driver;
    }

}
