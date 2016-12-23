package ui.viewcontroller.manager;

import blimpl.userblimpl.UserBLFactory;
import blservice.userblservice.UserBLService;
import ui.components.commontextfield.CommonTextField;
import ui.components.mydatepicker.MyDatePicker;
import ui.components.rectbutton.RectButton;
import ui.components.statebutton.StateButton;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import main.Main;
import ui.componentcontroller.common.AlertViewController;
import ui.viewcontroller.common.MainUIController;
import util.DateUtil;
import vo.ClientVO;

import java.io.IOException;

/**
 * Created by Kray on 2016/11/27.
 */
public class ClientManagementDetailEditViewController {

    private ClientManagementViewController clientManagementViewController;
    private ClientManagementListViewController clientManagementListViewController;
    private MainUIController mainUIController;

    private ClientVO clientVO;

    @FXML
    private Label clientIDLabel;

    @FXML
    private CommonTextField clientNameText;

    @FXML
    private Label accountLabel;

    @FXML
    private StateButton normalButton;

    @FXML
    private StateButton enterpriseButton;

    @FXML
    private CommonTextField enterpriseText;

    @FXML
    private MyDatePicker birthdayPicker;

    @FXML
    private Label creditLabel;

    @FXML
    private RectButton creditButton;

    @FXML
    private Label levelLabel;

    public void setClientManagementViewController(ClientManagementViewController clientManagementViewController) {
        this.clientManagementViewController = clientManagementViewController;
    }

    public void setMainUIController(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }

    public void showClientEdit(ClientVO clientVO) {
        this.clientVO = clientVO;

        clientIDLabel.setText(clientVO.clientID);
        clientNameText.setText(clientVO.clientName);
        accountLabel.setText(clientVO.account);
        if (clientVO.enterprise.equals("")) {
            enterpriseButton.setIsActiveProperty(false);
            normalButton.setIsActiveProperty(true);

            enterpriseText.setVisible(false);
        } else {
            enterpriseButton.setIsActiveProperty(true);
            normalButton.setIsActiveProperty(false);

            enterpriseText.setVisible(true);
            enterpriseText.setText(clientVO.enterprise);
        }

        levelLabel.setText("Lv." + clientVO.level);
        creditLabel.setText(clientVO.credit + "");
    }

    public void clickNormalButton() {
        normalButton.setIsActiveProperty(true);
        enterpriseButton.setIsActiveProperty(false);

        enterpriseText.setVisible(false);
    }

    public void clickEnterpriseButton() {
        normalButton.setIsActiveProperty(false);
        enterpriseButton.setIsActiveProperty(true);

        enterpriseText.setVisible(true);
    }

    public void clickBackButton() {
        clientManagementViewController.back();
        clientManagementViewController.back();
        clientManagementViewController.showClientDetail(clientVO);
    }

    public void clickSaveButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../component/common/AlertView.fxml"));
            AnchorPane pane = loader.load();

            AlertViewController alertViewController = loader.getController();

            alertViewController.setInfoLabel("确定保存信息吗？");
            alertViewController.setOnClickSureButton(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    sureSave();
                }
            });
            alertViewController.setOnClickCancelButton(new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
                    cancelSave();
                }
            });
            mainUIController.showPop(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sureSave() {
        mainUIController.hidePop();
        UserBLService userBLService = UserBLFactory.getUserBLServiceImpl_Client();
        clientVO.clientName = clientNameText.getText();
        DateUtil birthday = new DateUtil(birthdayPicker.getDate().getYear(),
                birthdayPicker.getDate().getMonthValue(), birthdayPicker.getDate().getDayOfMonth());
        clientVO.birthday = birthday;

        if (normalButton.getIsActiveProperty()) {
            clientVO.enterprise = "";
        } else {
            clientVO.enterprise = enterpriseText.getText();
        }

        userBLService.update(clientVO);

        this.clickBackButton();
    }

    private void cancelSave() {
        mainUIController.hidePop();
    }

}
