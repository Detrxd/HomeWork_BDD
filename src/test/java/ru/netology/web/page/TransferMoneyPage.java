package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TransferMoneyPage {

    SelenideElement count = $("[data-test-id='amount'] input");
    SelenideElement transferFrom = $("[data-test-id='from'] input");
    SelenideElement transferTo = $("[data-test-id='to']");
    SelenideElement passButton = $("[data-test-id='action-transfer']");
    SelenideElement declineButton = $("[data-test-id='action-cancel']");
    SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public TransferMoneyPage() {
        $("[class = 'heading heading_size_xl heading_theme_alfa-on-white']").shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Пополнение карты"));
    }

    private void fillAmountAndCardNumber(String amount, String cardNumber) {
        count.sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.DELETE));
        count.setValue(amount);
        transferFrom.sendKeys(Keys.chord(Keys.CONTROL, "A", Keys.DELETE));
        transferFrom.setValue(cardNumber);
        passButton.click();
    }

    public DashboardPage transferSuccess(String amount, String cardNumber) {
        fillAmountAndCardNumber(amount, cardNumber);
        return new DashboardPage();
    }
    public void transferFault(String amount, String cardNumber) {
        fillAmountAndCardNumber(amount, cardNumber);
        errorNotification.shouldBe(Condition.visible);
    }
    public DashboardPage cancelTransferMoney(){
        declineButton.click();
        return new DashboardPage();
    }

}
