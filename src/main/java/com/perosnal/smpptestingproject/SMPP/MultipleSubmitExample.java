package com.perosnal.smpptestingproject.SMPP;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Formatter;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Address;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.ReplaceIfPresentFlag;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.SubmitMultiResult;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.DeliveryReceiptState;

public class MultipleSubmitExample {
//	private static final Logger LOGGER = LoggerFactory.getLogger(MultipleSubmitExample.class);

//    private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();

	private final String smppIp = "10.226.9.88";

	private int port = 15019;

	private final String username = "MYJAZZAPP";

	private final String password = "m0Bcon!1";

	private final String address = "10.50.20.57";

	private static final String SERVICE_TYPE = "CMT";

	public void broadcastMessage(String message, List numbers) {
//        LOGGER.info("Broadcasting sms");
		System.out.println("Broadcasting sms");
		System.out.println("SMPP IP -> " + smppIp + " port -> " + port + " UserName -> " + username + " Password -> "
				+ password + " Source Address -> " + address);
		;
		SubmitMultiResult result = null;
		Address[] addresses = prepareAddress(numbers);
		SMPPSession session = initSession();
		if (session != null) {
			try {
				result = session.submitMultiple(SERVICE_TYPE, TypeOfNumber.NATIONAL, NumberingPlanIndicator.LAND_MOBILE,
						address, addresses, new ESMClass(), (byte) 0, (byte) 1, "", null,
						new RegisteredDelivery(SMSCDeliveryReceipt.FAILURE), ReplaceIfPresentFlag.REPLACE,
						new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0,
						message.getBytes());

//				LOGGER.info("Messages submitted, result is {}", result);
				System.out.println("Messages submitted, result is {} " + result);
				Thread.sleep(1000);
			} catch (PDUException e) {
//				LOGGER.error("Invalid PDU parameter", e);
				System.out.println("Invalid PDU parameter" + e);
			} catch (ResponseTimeoutException e) {
//				LOGGER.error("Response timeout", e);
				System.out.println("Response timeout");
			} catch (InvalidResponseException e) {
//				LOGGER.error("Receive invalid response", e);
				System.out.println("Receive invalid response -> " + e);
			} catch (NegativeResponseException e) {
//				LOGGER.error("Receive negative response", e);
				System.out.println("Receive negative response -> " + e);
			} catch (IOException e) {
//				LOGGER.error("I/O error occured", e);
				System.out.println("I/O error occured -> " + e);
			} catch (Exception e) {
//				LOGGER.error("Exception occured submitting SMPP request", e);
				System.out.println("Exception occured submitting SMPP request -> " + e);
			}
		} else {
//			LOGGER.error("Session creation failed with SMPP broker.");
			System.out.println("Session creation failed with SMPP broker.");
		}
		if (result != null && result.getUnsuccessDeliveries() != null && result.getUnsuccessDeliveries().length > 0) {
//			LOGGER.error(DeliveryReceiptState.getDescription(result.getUnsuccessDeliveries()[0].getErrorStatusCode())
//					.description() + " - " + result.getMessageId());
			System.out.println("Result " + result);
		} else {
//			LOGGER.info("Pushed message to broker successfully");
		}
		if (session != null) {
			session.unbindAndClose();
			System.out.println("Session Unbind");
		}
	}

	private Address[] prepareAddress(List numbers) {
		Address[] addresses = new Address[numbers.size()];
		System.out.println("Addresses -> " + addresses.toString());
		for (int i = 0; i < numbers.size(); i++) {
			System.out.println("Addresses -> " + numbers.get(i));
			addresses[i] = new Address(TypeOfNumber.NATIONAL, NumberingPlanIndicator.UNKNOWN, (String) numbers.get(i));
		}
		System.out.println("Returning Addresses -> " + address);
		return addresses;
	}

	private SMPPSession initSession() {
		SMPPSession session = new SMPPSession();
		try {
			session.setMessageReceiverListener(new MessageReceiverListenerImpl());
			System.out.println("Set Message Receiver Listener");
			String systemId = session.connectAndBind(smppIp, Integer.valueOf(port), new BindParameter(BindType.BIND_TX,
					username, password, "cp", TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
			System.out.println("SystemId Retunred -> " + systemId);
			// LOGGER.info("Connected with SMPP with system id {}", systemId);
		} catch (IOException e) {
//			LOGGER.error("I/O error occured", e);
			session = null;
			System.out.println("I/O Error Exception Occured");
		}
		return session;
	}

	public static void main(String[] args) {
		MultipleSubmitExample multiSubmit = new MultipleSubmitExample();
		multiSubmit.broadcastMessage("Test message from Fazeel", Arrays.asList("923037512284", "923037512284"));
	}
}
