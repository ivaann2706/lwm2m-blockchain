package utils;

import java.math.BigInteger;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import wrappers.AnomalyStore;
import wrappers.ClientStore;
import wrappers.UserStore;


public class DeployContract {

	static Logger log = LoggerFactory.getLogger(DeployContract.class);

	public static void main(String[] args) {

		System.out.println("BEGIN deployContract");
		//Ropsten
		String url = "https://ropsten.infura.io/v3/a21979a509154e19b42267c28f697e32";
		String privateKey = "4C2A99F86C06C98448AB1986D33A248D699B5D7280EEBD76E4FD60B84C4B51C8"; 

		BigInteger gasPrice = new BigInteger("20000000000");
		BigInteger gasLimit = new BigInteger("4712388");
		ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
		Web3j web3j;
		ClientStore clientContract;
		UserStore userContract;
		AnomalyStore anomalyContract;

		try {
			web3j = Web3j.build(new HttpService(url)); // Connect to Ethereum node
			Credentials credentials = Credentials.create(privateKey); // Create credentials
			//Deploy smart contract
//			clientContract = ClientStore.deploy(web3j, credentials, gasProvider, new ArrayList<String>()).send(); 
//			userContract = UserStore.deploy(web3j, credentials, gasProvider, new ArrayList<String>()).send();
//			anomalyContract = AnomalyStore.deploy(web3j, credentials, gasProvider, new ArrayList<String>()).send();
//
//			System.out.println("ClientContract Address: " + clientContract.getContractAddress());
//			System.out.println("UserContract Address: " + userContract.getContractAddress());
//			System.out.println("AnomalyContract Address: " + anomalyContract.getContractAddress());

		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("END deployContract");
	}
}




