package com.ivan.ServerLeshan.service;

import java.math.BigInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple3;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import com.ivan.ServerLeshan.bean.Credentials;
import com.ivan.ServerLeshan.utils.Converter;
import com.ivan.ServerLeshan.wrappers.UserStore;

@Service
public class LoginService {

	static Logger log = LoggerFactory.getLogger(LoginService.class);

	String url = ""; //TO COMPLETE USING INFURA
	String privateKey = ""; //TO COMPLETE
	String contractAddress = "0xcfc79c76fe6c55445fa9301be5b2313dbba2d9ea"; //Ropsten

	BigInteger gasPrice = new BigInteger("40000000000");
	BigInteger gasLimit = new BigInteger("4712388");
	ContractGasProvider gasProvider = new StaticGasProvider(gasPrice, gasLimit);
	Web3j web3j;
	UserStore contract;

	public LoginService() {
		try {
			web3j = connect(url);
			org.web3j.crypto.Credentials credentials = createCredentials(privateKey);
			contract = loadContract(web3j, credentials, contractAddress, gasProvider);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Connect to Ethereum node
	private Web3j connect(String url) throws Exception {
		Web3j web3j = Web3j.build(new HttpService(url));
		log.info("Connected to Ethereum client version: " + web3j.web3ClientVersion().send().getWeb3ClientVersion());
		return web3j;
	}

	// Create credentials
	private org.web3j.crypto.Credentials createCredentials(String privateKey) throws Exception {
		org.web3j.crypto.Credentials credentials = org.web3j.crypto.Credentials.create(privateKey);
		log.info("Credentials created");
		return credentials;
	}

	// Load smart contract
	private UserStore loadContract(Web3j web3j, org.web3j.crypto.Credentials credentials, String address, ContractGasProvider provider) throws Exception {
		UserStore contract = UserStore.load(address, web3j, credentials, provider);
		log.info("Smart contract loaded");
		return contract;
	}
	
	public Credentials validateLogin(String wildcard, String password) {
		Credentials c = new Credentials("", "", 0, "");
		try {
			byte[] wildcard_bytes = Converter.asciiToByte32(wildcard);
			byte[] password_bytes = Converter.asciiToByte32(password);
			Tuple3<byte[], byte[], BigInteger> response = contract.validateLogin(wildcard_bytes, password_bytes).send();
			c = this.tuple3ToList(response);
			return c;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	
	private Credentials tuple3ToList(Tuple3<byte[], byte[], BigInteger> tuple) {
		String username = Converter.byteToAscii(tuple.component1());
		String email = Converter.byteToAscii(tuple.component2());
		int role = tuple.component3().intValue();

		Credentials c = new Credentials(username, email, role, "");
		return c;
	}
}
