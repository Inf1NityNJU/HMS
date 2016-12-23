package ui.componentcontroller.hotel;

import blimpl.blfactory.BLFactoryImpl;
import blservice.hotelblservice.HotelBLService;
import ui.components.circlebutton.CircleButton;
import ui.components.circleimage.CircleImage;
import ui.components.tinybutton.TinyButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import ui.viewcontroller.staff.RoomAvailableListViewController;
import util.DateUtil;
import util.ResultMessage;
import vo.HotelRoomVO;
import vo.RoomChangeInfoVO;
import vo.RoomStockVO;

/**
 * Created by SilverNarcissus on 2016/11/29.
 */
public class RoomAvailableCellController {

    @FXML
    private CircleImage imageView;

    @FXML
    private Label roomTypeLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label totalQuantityLabel;

    @FXML
    private Label availableQuantityLabel;

    @FXML
    private CircleButton minusButton;

    @FXML
    private CircleButton plusButton;

    @FXML
    private TinyButton editButton;

    private HotelRoomVO hotelRoom;
    private RoomStockVO roomStock;

    private RoomAvailableListViewController roomAvailableListViewController;

    private HotelBLService hotelBLService = new BLFactoryImpl().getHotelBLService();

    private DateUtil date;
    private int availableQuantity;
    private boolean editMode;

    public void setRoomAvailableListViewController(RoomAvailableListViewController roomAvailableListViewController) {
        this.roomAvailableListViewController = roomAvailableListViewController;
    }

    public void setRoom(HotelRoomVO hotelRoom, DateUtil date) {
        this.hotelRoom = hotelRoom;
        this.date = date;

        imageView.setImage(new Image(getClass().getResource("/images/room/" + hotelRoom.roomType.toString() + ".png").toExternalForm()));
        imageView.setRadius(40);

        roomTypeLabel.setText(hotelRoom.roomType.getName());
        priceLabel.setText("¥ " + String.format("%.2f", hotelRoom.price));

        totalQuantityLabel.setText(hotelRoom.totalQuantity + " 间");

        for (RoomStockVO roomStock : hotelRoom.roomStockVOs) {
            if (roomStock.date.equals(date)) {
                this.roomStock = roomStock;
                break;
            }
        }

        if (roomStock == null) {
            System.out.println("Can't find roomStockVO");

        } else {
            availableQuantity = roomStock.availableQuantity;
            availableQuantityLabel.setText(availableQuantity + "");
        }

        setEditMode(false);
    }

    @FXML
    private void clickMinusButton() {
        availableQuantity--;
        refreshQuantity();
    }

    @FXML
    private void clickPlusButton() {
        availableQuantity++;
        refreshQuantity();
    }

    private void refreshQuantity() {
        minusButton.setAbled(availableQuantity > 1);
        plusButton.setAbled(availableQuantity < hotelRoom.totalQuantity);

        availableQuantityLabel.setText(availableQuantity + "");
    }

    private void setEditMode(boolean editMode) {
        this.editMode = editMode;

        minusButton.setVisible(editMode);
        plusButton.setVisible(editMode);

        editButton.setText(editMode ? "保存" : "编辑");
    }

    @FXML
    public void clickEditButton() {
        if (!editMode) {
            setEditMode(true);

        } else {
            RoomChangeInfoVO roomChangeInfo = new RoomChangeInfoVO(date, date, hotelRoom.hotelID, hotelRoom.roomType, roomStock.availableQuantity - availableQuantity);
            ResultMessage resultMessage = hotelBLService.updateHotelRoomQuantity(roomChangeInfo);

            if (resultMessage.equals(ResultMessage.SUCCESS)) {
//            roomAvailableListViewController.showAvailableRoomList();
                setEditMode(false);
            } else {
                System.out.println(resultMessage);
            }
        }

    }
}


