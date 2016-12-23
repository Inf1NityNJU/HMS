package ui.componentcontroller.hotel;

import blimpl.blfactory.BLFactoryImpl;
import blservice.userblservice.UserBLInfo;
import ui.components.rectbutton.RectButton;
import ui.components.statebutton.StateButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.viewcontroller.manager.HotelManagementListViewController;
import vo.*;

/**
 * Created by Sorumi on 16/11/18.
 */
public class ManagerHotelCellController {

    private HotelManagementListViewController hotelManagementListViewController;
    private Hotel_DetailVO hotel;

    @FXML
    private ImageView imageView;

    @FXML
    private Label hotelNameLabel;

    @FXML
    private Label hotelIDLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label staffLabel;

    @FXML
    private StateButton cityLabel;

    @FXML
    private StateButton placeLabel;

    @FXML
    private RectButton detailButton;

    public void setHotelManagementListViewController(HotelManagementListViewController hotelManagementListViewController) {
        this.hotelManagementListViewController = hotelManagementListViewController;
    }

    public void setHotel(Hotel_DetailVO hotel) {
        this.hotel = hotel;

        int num = hotel.ID.charAt(7) - '0';
        imageView.setImage(new Image(getClass().getResource("/images/hotel/" + num + ".png").toExternalForm()));

        hotelNameLabel.setText(hotel.name);
        hotelIDLabel.setText(hotel.ID);
        addressLabel.setText(hotel.address);
        cityLabel.setText(hotel.city.getName());
        placeLabel.setText(hotel.place.getName());
        UserBLInfo userBLInfo = new BLFactoryImpl().getUserBLInfo_Staff();
        StaffVO staff = userBLInfo.getStaffByHotelID(hotel.ID);
        if (staff != null) {
            staffLabel.setText(staff.staffName + " " + staff.staffID);
        } else {
            staffLabel.setText("无");
        }

    }

    @FXML
    private void clickDetailButton() {
        hotelManagementListViewController.showHotelDetail(hotel);
    }


}
