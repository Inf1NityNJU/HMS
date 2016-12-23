package ui.componentcontroller.promotion;

import ui.components.statebutton.StateButton;
import ui.components.tinybutton.TinyButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import vo.PromotionVO;

/**
 * Created by vivian on 16/11/29.
 */
public class WebPromotionCellController {
    private PromotionVO promotionVO;

    private WebPromotionPagePaneController webPromotionPagePaneController;

    @FXML
    private Label promotionNameLabel;

    @FXML
    private StateButton promotionType;

    @FXML
    private Label promotionDiscountLabel;

    @FXML
    private TinyButton detailButton;

    public void setWebPromotionPagePaneController(WebPromotionPagePaneController webPromotionPagePaneController) {
        this.webPromotionPagePaneController = webPromotionPagePaneController;
    }

    public void setPromotionVO(PromotionVO promotionVO){
        this.promotionVO = promotionVO;

        promotionNameLabel.setText(promotionVO.promotionName);
        promotionType.setText(promotionVO.promotionType.getType());
        promotionType.setColorProperty(promotionVO.promotionType.getColor());
        promotionDiscountLabel.setText(Double.toString(promotionVO.promotionDiscount));
    }

    @FXML
    public void clickDetailButton(){
        webPromotionPagePaneController.showPromotionDetail(promotionVO);
    }
}
