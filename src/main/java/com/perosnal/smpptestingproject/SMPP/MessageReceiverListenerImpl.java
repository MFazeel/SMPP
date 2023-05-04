package com.perosnal.smpptestingproject.SMPP;

import org.jsmpp.SMPPConstant;
import org.jsmpp.bean.AlertNotification;
import org.jsmpp.bean.DataSm;
import org.jsmpp.bean.DeliverSm;
import org.jsmpp.bean.DeliveryReceipt;
import org.jsmpp.bean.MessageType;
import org.jsmpp.extra.ProcessRequestException;
import org.jsmpp.session.DataSmResult;
import org.jsmpp.session.MessageReceiverListener;
import org.jsmpp.session.Session;
import org.jsmpp.util.InvalidDeliveryReceiptException;

public class MessageReceiverListenerImpl implements MessageReceiverListener {

//	private static final Logger LOGGER = LoggerFactory.getLogger(MessageReceiverListenerImpl.class);
	private static final String DATASM_NOT_IMPLEMENTED = "data_sm not implemented";

	public void onAcceptDeliverSm(DeliverSm deliverSm) throws ProcessRequestException {

		if (MessageType.SMSC_DEL_RECEIPT.containedIn(deliverSm.getEsmClass())) {

			try {
				DeliveryReceipt delReceipt = deliverSm.getShortMessageAsDeliveryReceipt();

				long id = Long.parseLong(delReceipt.getId()) & 0xffffffff;
				String messageId = Long.toString(id, 16).toUpperCase();

//				LOGGER.info("Receiving delivery receipt for message '{}' from {} to {}: {}", messageId,
//						deliverSm.getSourceAddr(), deliverSm.getDestAddress(), delReceipt);
			} catch (InvalidDeliveryReceiptException e) {
//				LOGGER.error("Failed getting delivery receipt", e);
			}
		}
	}

	public void onAcceptAlertNotification2(AlertNotification alertNotification) {
//		LOGGER.info("AlertNotification not implemented");
	}

	public DataSmResult onAcceptDataSm2(DataSm dataSm, Session source) throws ProcessRequestException {
//		LOGGER.info("DataSm not implemented");
		throw new ProcessRequestException(DATASM_NOT_IMPLEMENTED, SMPPConstant.STAT_ESME_RINVCMDID);
	}

	public DataSmResult onAcceptDataSm(DataSm dataSm, Session source) throws ProcessRequestException {
		// TODO Auto-generated method stub
		return null;
	}

	public void onAcceptDeliverSm2(DeliverSm deliverSm) throws ProcessRequestException {
		// TODO Auto-generated method stub

	}

	public void onAcceptAlertNotification(AlertNotification alertNotification) {
		// TODO Auto-generated method stub

	}
}
