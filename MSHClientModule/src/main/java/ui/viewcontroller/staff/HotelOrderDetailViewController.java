package ui.viewcontroller.staff;

import blimpl.blfactory.BLFactoryImpl;
import blservice.orderblservice.OrderBLService;
import ui.components.mycheckbox.MyCheckBox;
import ui.components.ratestarpane.RateStarPane;
import ui.components.statebutton.StateButton;
import ui.components.tinybutton.TinyButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ui.componentcontroller.order.OrderRoomCellController;
import ui.componentcontroller.promotion.OrderPromotionCellController;
import util.OrderState;
import util.TimeUtil;
import vo.AssessmentVO;
import vo.OrderRoomVO;
import vo.OrderVO;
import vo.PromotionVO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by Sorumi on 16/11/22.
 */
public class HotelOrderDetailViewController {

    private HotelOrderViewController hotelOrderViewController;

    @FXML
    private Label orderIDLabel;

    @FXML
    private Label hotelNameLabel;

    @FXML
    private Label bookTimeLabel;

    @FXML
    private StateButton stateLabel;

    @FXML
    private Label cancelledLabel;

    @FXML
    private Label cancelledTimeLabel;

    @FXML
    private Label checkDateLabel;

    @FXML
    private Label checkInTimeLabel;

    @FXML
    private TinyButton updateCheckInButton;

    @FXML
    private TinyButton updateCheckOutButton;

    @FXML
    private Label checkOutTimeLabel;

    @FXML
    private Label latestExecuteDateLabel;

    @FXML
    private Label latestExecuteTimeLabel;

    @FXML
    private Label peopleQuantityLabel;

    @FXML
    private MyCheckBox hasChildrenCheckBox;

    @FXML
    private VBox roomVBox;

    @FXML
    private VBox promotionVBox;

    @FXML
    private Label originPriceLabel;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Label noAssessmentLabel;

    @FXML
    private GridPane scorePane;

    @FXML
    private RateStarPane serviceScorePane;

    @FXML
    private RateStarPane facilityScorePane;

    @FXML
    private RateStarPane healthScorePane;

    @FXML
    private RateStarPane locationScorePane;

    @FXML
    private Text commentText;

    private OrderVO order;

    private OrderBLService orderBLService = new BLFactoryImpl().getOrderBLService();

    public void setHotelOrderViewController(HotelOrderViewController hotelOrderViewController) {
        this.hotelOrderViewController = hotelOrderViewController;
    }

    public void showOrder(OrderVO order) {
        this.order = order;

        orderIDLabel.setText(order.orderID);
        hotelNameLabel.setText(order.hotelName);
        bookTimeLabel.setText(order.bookedTime.toString());
        checkDateLabel.setText(order.checkInDate.toString() + " - " + order.checkOutDate.toString());
        latestExecuteDateLabel.setText(order.latestExecuteTime.date.toString());
        latestExecuteTimeLabel.setText(order.latestExecuteTime.timeString());
        peopleQuantityLabel.setText(order.peopleQuantity + "");
        hasChildrenCheckBox.setIsAbledProperty(false);
        hasChildrenCheckBox.setIsActiveProperty(order.hasChildren);
        originPriceLabel.setText("¥ " + order.bill.originPrice);
        totalPriceLabel.setText("¥ " + order.bill.totalPrice);

        updateState();

        addRooms(order.rooms);

        if (order.bill.websitePromotion != null) {
            addPromotion(order.bill.websitePromotion);
        }
        if (order.bill.hotelPromotion != null) {
            addPromotion(order.bill.hotelPromotion);
        }

    }

    private void updateState() {
        stateLabel.setText(order.state.getName());
        stateLabel.setColorProperty(order.state.getColor());

        if (order.checkInTime == null && order.checkOutTime == null) {
            checkInTimeLabel.setText("未入住");
            checkOutTimeLabel.setText("未入住");
            updateCheckInButton.setVisible(true);
            updateCheckOutButton.setVisible(false);

        } else if (order.checkInTime != null && order.checkOutTime == null){
            checkInTimeLabel.setText(order.checkInTime.toString());
            checkOutTimeLabel.setText("未退房");
            updateCheckInButton.setVisible(false);
            updateCheckOutButton.setVisible(true);
        } else {
            checkInTimeLabel.setText(order.checkInTime.toString());
            checkOutTimeLabel.setText(order.checkOutTime.toString());
            updateCheckInButton.setVisible(false);
            updateCheckOutButton.setVisible(false);
        }

        OrderState state = order.state;
        if (state == OrderState.Cancelled) {
            cancelledLabel.setVisible(true);
            cancelledTimeLabel.setVisible(true);
            cancelledTimeLabel.setText(order.cancelledTime.toString());
        } else {
            cancelledLabel.setVisible(false);
            cancelledTimeLabel.setVisible(false);
        }


        if (state == OrderState.Abnormal) {
            updateCheckInButton.setText("延迟入住");
            updateCheckInButton.setVisible(true);
            updateCheckOutButton.setVisible(false);
        }

        if (state == OrderState.Executed) {
            AssessmentVO assessment = order.assessment;
            if (assessment == null) {
                setAssessmentDisplay(false);

            } else {
                setAssessmentDisplay(true);

                serviceScorePane.setScore(assessment.serviceScore);
                facilityScorePane.setScore(assessment.facilityScore);
                healthScorePane.setScore(assessment.healthScore);
                locationScorePane.setScore(assessment.locationScore);
                commentText.setText(assessment.comment);
            }
        } else {
            setAssessmentDisplay(false);
        }

    }

    private void addRooms(ArrayList<OrderRoomVO> rooms) {
        for (OrderRoomVO room : rooms) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/component/order/OrderRoomCell.fxml"));
                Pane pane = loader.load();

                OrderRoomCellController orderRoomCellController = loader.getController();
                orderRoomCellController.setRoom(room);

                roomVBox.getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addPromotion(PromotionVO promotion) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/component/promotion/OrderPromotionCell.fxml"));
            Pane pane = loader.load();

            OrderPromotionCellController orderPromotionCellController = loader.getController();
            orderPromotionCellController.setPromotion(promotion);

            promotionVBox.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void clickCheckInButton() {
        orderBLService.checkInOrder(order.orderID, new TimeUtil(LocalDateTime.now()));
        order = orderBLService.searchOrderByID(order.orderID);
        updateState();
    }

    @FXML void clickCheckOutButton() {
        orderBLService.checkOutOrder(order.orderID, new TimeUtil(LocalDateTime.now()));
        order = orderBLService.searchOrderByID(order.orderID);
        updateState();
    }

    @FXML
    private void clickBackButton() {
        hotelOrderViewController.refreshHotelOrderList();
        hotelOrderViewController.back();
    }

    private void setAssessmentDisplay(boolean display) {
        noAssessmentLabel.setVisible(!display);
        noAssessmentLabel.setManaged(!display);
        scorePane.setVisible(display);
        scorePane.setManaged(display);
        commentText.setVisible(display);
        commentText.setManaged(display);
    }
}

