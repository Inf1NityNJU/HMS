package ui.viewcontroller.client;

import blimpl.blfactory.BLFactoryImpl;
import blservice.hotelblservice.HotelBLService;
import blservice.orderblservice.OrderBLInfo;
import blservice.orderblservice.OrderBLService;
import blservice.promotionblservice.PromotionBLService;
import blservice.userblservice.UserBLInfo;
import ui.components.mydatepicker.MyDatePicker;
import ui.components.ratestarpane.RateStarPane;
import ui.components.rectbutton.RectButton;
import ui.components.starlabel.StarLabel;
import ui.components.statebutton.StateButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import main.Main;
import ui.componentcontroller.common.AlertViewController;
import ui.componentcontroller.order.HotelAssessmentCellController;
import ui.componentcontroller.promotion.OrderPromotionCellController;
import ui.componentcontroller.hotel.ClientHotelRoomCellController;
import ui.viewcontroller.common.MainUIController;
import util.DateUtil;
import util.ResultMessage;
import vo.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Created by Sorumi on 16/12/2.
 */
public class ClientHotelDetailViewController {

    @FXML
    private ImageView imageView;

    @FXML
    private VBox promotionVBox;

    @FXML
    private VBox roomVBox;

    @FXML
    private VBox commentVBox;

    @FXML
    private MyDatePicker checkInDatePicker;

    @FXML
    private MyDatePicker checkOutDatePicker;

    @FXML
    private Label nameLabel;

    @FXML
    private StarLabel starLabel;

    @FXML
    private Label bookedLabel;

    @FXML
    private StateButton cityButton;

    @FXML
    private StateButton placeButton;

    @FXML
    private Label addressLabel;

    @FXML
    private Text introductionText;

    @FXML
    private Text facilitiesText;

    @FXML
    private Label bookRoomLabel;

    @FXML
    private RectButton bookButton;

    @FXML
    private Label assessmentCountLabel;

    @FXML
    private RateStarPane rateScorePane;

    @FXML
    private Label scoreLabel;

    @FXML
    private RateStarPane serviceScorePane;

    @FXML
    private Label serviceScoreLabel;

    @FXML
    private RateStarPane facilityScorePane;

    @FXML
    private Label facilityScoreLabel;

    @FXML
    private RateStarPane healthScorePane;

    @FXML
    private Label healthScoreLabel;

    @FXML
    private RateStarPane locationScorePane;

    @FXML
    private Label locationScoreLabel;

    private OrderVO order;

    private MainUIController mainUIController;
    private ClientSearchHotelViewController clientSearchHotelViewController;

    private HotelBLService hotelBLService = new BLFactoryImpl().getHotelBLService();
    private OrderBLService orderBLService = new BLFactoryImpl().getOrderBLService();
    private OrderBLInfo orderBLInfo = new BLFactoryImpl().getOrderBLInfo();
    private PromotionBLService promotionBLService = new BLFactoryImpl().getPromotionBLService();
    private UserBLInfo userBLInfo = new BLFactoryImpl().getUserBLInfo_Client();


    private Hotel_DetailVO hotel;

    private ArrayList<OrderRoomStockVO> roomStocks;
    private int bookRoomQuantity;

    public void setMainUIController(MainUIController mainUIController) {
        this.mainUIController = mainUIController;
    }

    public void setClientSearchHotelViewController(ClientSearchHotelViewController clientSearchHotelViewController) {
        this.clientSearchHotelViewController = clientSearchHotelViewController;
    }

    public void setHotel(Hotel_DetailVO hotel) {
        this.hotel = hotel;

        //image
        int num = hotel.ID.charAt(7) - '0';
        Image image = new Image(getClass().getResource("/images/hotel/" + num + ".png").toExternalForm());

        double width = image.getWidth();
        double height = image.getHeight();
        Rectangle2D viewportRect = new Rectangle2D(0, (height-400)/2, width, width*2/5);
        imageView.setViewport(viewportRect);
        imageView.setImage(image);

        //Add hotel
        nameLabel.setText(hotel.name);
        starLabel.setStar(hotel.star);
        cityButton.setText(hotel.city.getName());
        placeButton.setText(hotel.place.getName());
        addressLabel.setText(hotel.address);
        introductionText.setText(hotel.introduction);
        facilitiesText.setText(hotel.facilities);
        scoreLabel.setText(String.valueOf(hotel.score)+"分");
        rateScorePane.setScore((int)hotel.score);

        checkInDatePicker.setDate(LocalDate.now());
        checkInDatePicker.setMinDate(LocalDate.now());
        checkInDatePicker.setMaxDate(LocalDate.now().plusDays(28));
        checkOutDatePicker.setDate(checkInDatePicker.getDate().plusDays(1));
        checkOutDatePicker.setMinDate(checkInDatePicker.getDate().plusDays(1));
        checkOutDatePicker.setMaxDate(LocalDate.now().plusDays(29));
        checkInDatePicker.dateProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                checkOutDatePicker.setMinDate(newValue.plusDays(1));
                if (checkOutDatePicker.getDate().isBefore(newValue)) {
                    checkOutDatePicker.setDate(newValue.plusDays(1));
                }
                newOrder();
            }
        });
        checkOutDatePicker.dateProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                newOrder();
            }
        });

        String clientID = userBLInfo.getCurrentClientID();
        boolean isBooked = orderBLInfo.isBookedHotelByClient(hotel.ID, clientID);
        bookedLabel.setVisible(isBooked);

        addPromotions();
        addAssessment();
        newOrder();

    }

    public void newOrder() {
        String pattern = "yyyy-MM-dd";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

        order = new OrderVO(hotel.ID, new DateUtil(checkInDatePicker.getDate().format(dateFormatter)), new DateUtil(checkOutDatePicker.getDate().format(dateFormatter)));
        order.rooms = new ArrayList<>();
        order.hotelName = hotel.name;
        orderBLService.startOrder(order);
        addRooms();

        bookRoomQuantity = 0;
        bookRoomLabel.setText("");
        bookButton.setVisible(false);
    }


    private void addPromotions() {
        ArrayList<PromotionVO> promotions = promotionBLService.searchHotelPromotions(hotel.ID);

        for (PromotionVO promotion : promotions) {
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
    }

    private void addRooms() {
        roomVBox.getChildren().clear();

        roomStocks = hotelBLService.getRoomStocks(new DateUtil(checkInDatePicker.getDate()), new DateUtil(checkOutDatePicker.getDate().plusDays(-1)), hotel.ID);

        for (OrderRoomStockVO room : roomStocks) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/component/hotel/ClientHotelRoomCell.fxml"));
                Pane pane = loader.load();

                ClientHotelRoomCellController clientHotelRoomCellController = loader.getController();
                clientHotelRoomCellController.setClientHotelDetailViewController(this);
                clientHotelRoomCellController.setRoom(room);

                roomVBox.getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addRoomInOrder(OrderRoomStockVO orderRoomStock) {
        ResultMessage rm = orderBLService.checkCredit();
        if (rm == ResultMessage.INSUFFICIENT) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../component/common/AlertView.fxml"));
                AnchorPane pane = loader.load();

                AlertViewController alertViewController = loader.getController();
                alertViewController.setInfoLabel("信用值不足！可进行线下信用充值！");
                alertViewController.hideLeftButton();
                alertViewController.setOnClickSureButton(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        mainUIController.hidePop();
                    }
                });
                mainUIController.showPop(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        bookRoomQuantity++;
        bookButton.setVisible(true);
        bookRoomLabel.setText("已定 " + bookRoomQuantity + " 间");
    }

    @FXML
    private void clickBookButton() {

        for (OrderRoomStockVO room : roomStocks) {
            if (room.orderRoom.quantity > 0) {
                OrderRoomVO roomVO = room.orderRoom;
                order.rooms.add(roomVO);
            }
        }

        clientSearchHotelViewController.showBookOrder(order);
    }

    private void addAssessment() {
        commentVBox.getChildren().clear();

        ArrayList<Assessment_HotelVO> assessment_hotelVOs = orderBLInfo.getAssessmentByHotelID(hotel.ID);

        int size = assessment_hotelVOs.size();
        assessmentCountLabel.setText(size + " 条评论");

        double score = 0;
        double serviceScore = 0;
        double facilityScore = 0;
        double healthScore = 0;
        double locationScore = 0;

        for (Assessment_HotelVO assessment_hotelVO : assessment_hotelVOs) {

            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/component/order/HotelAssessmentCell.fxml"));
                Pane pane = loader.load();

                HotelAssessmentCellController hotelAssessmentCellController = loader.getController();
                hotelAssessmentCellController.setAssessment(assessment_hotelVO);

                commentVBox.getChildren().add(pane);

            } catch (IOException e) {
                e.printStackTrace();
            }

            score += assessment_hotelVO.serviceScore + assessment_hotelVO.healthScore + assessment_hotelVO.facilityScore + assessment_hotelVO.locationScore;
            serviceScore += assessment_hotelVO.serviceScore;
            facilityScore += assessment_hotelVO.facilityScore;
            healthScore += assessment_hotelVO.healthScore;
            locationScore += assessment_hotelVO.locationScore;
        }

        if (size != 0) {
            score = score/size/4.0;
            serviceScore /= size;
            facilityScore /= size;
            healthScore /= size;
            locationScore /= size;
        }

        scoreLabel.setText(String.format("%.1f", score) + " 分");
        rateScorePane.setScore((int)score);

        serviceScoreLabel.setText(String.format("%.1f", serviceScore) + " 分");
        serviceScorePane.setScore((int)serviceScore);
        facilityScoreLabel.setText(String.format("%.1f", facilityScore) + " 分");
        facilityScorePane.setScore((int)facilityScore);
        healthScoreLabel.setText(String.format("%.1f", healthScore) + " 分");
        healthScorePane.setScore((int)healthScore);
        locationScoreLabel.setText(String.format("%.1f", locationScore) + " 分");
        locationScorePane.setScore((int)locationScore);
    }


    @FXML
    private void clickBackButton() {
        clientSearchHotelViewController.back();
    }
}
