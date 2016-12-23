package ui.viewcontroller.manager;

import blimpl.userblimpl.UserBLFactory;
import blservice.userblservice.UserBLService;
import ui.components.rectbutton.RectButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import vo.SalesmanVO;

/**
 * Created by Kray on 2016/11/26.
 */
public class SalesmanManagementDetailViewController {

    private SalesmanVO salesmanVO;

    private WorkerManagementViewController workerManagementViewController;

    @FXML
    private Label accountLabel;

    @FXML
    private Label salesmanIDLabel;

    @FXML
    private Label salesmanNameLabel;

    @FXML
    private RectButton passwordButton;

    @FXML
    private RectButton backButton;

    @FXML
    private RectButton deleteButton;

    @FXML
    private RectButton editButton;

    public void setWorkerManagementViewController(WorkerManagementViewController workerManagementViewController) {
        this.workerManagementViewController = workerManagementViewController;
    }

    public void showSalesman(SalesmanVO salesmanVO){
        this.salesmanVO = salesmanVO;

        accountLabel.setText(salesmanVO.account);
        salesmanIDLabel.setText(salesmanVO.salesmanID);
        salesmanNameLabel.setText(salesmanVO.salesmanName);
    }

    public void clickBackButton() {
        workerManagementViewController.back();
        workerManagementViewController.getWorkerManagementListViewController().showAllWorkers();
    }

    public void clickPasswordButton() {
        workerManagementViewController.resetPassword(salesmanVO.account, salesmanVO.salesmanID);
    }

    public void clickDeleteButton() {
        UserBLService userBLService = UserBLFactory.getUserBLServiceImpl_Salesman();
        userBLService.delete(salesmanVO.salesmanID);

        clickBackButton();
    }

    public void clickEditButton() {
        workerManagementViewController.editSalesmanDetail(salesmanVO);
    }

}
