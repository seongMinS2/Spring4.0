package net.madvirus.spring4.chap02.search;

import org.springframework.beans.factory.FactoryBean;

public class SearchClientFactoryBean implements FactoryBean<SearchClientFactory>{

	private SearchClientFactory searchClientFactory;
	
	private String server;
	private int port;
	private String contentType;
	private String encoding = "utf8";
	
	public void setServer(String server) {
		this.server = server;
	}

	public void setPort(int port) {
		this.port = port;
	} 

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	//빈 객체로 사용될 SearchClientFactory객체를 생성하고 이를 리턴
	@Override
	public SearchClientFactory getObject() throws Exception {
		
		//searchClientFactory필드가 null이 아니면 필드를 리턴
		if(this.searchClientFactory != null)
		return this.searchClientFactory;
		
		//SearchClientFactoryBuilder 객체를 통해 searchClientFactory 객체를 생성
		SearchClientFactoryBuilder builder = new SearchClientFactoryBuilder();
		builder.server(server)
			.port(port)
			.contentType(contentType==null?"json":contentType)
			.encoding(encoding);
		SearchClientFactory searchClientFactory = builder.build();
		searchClientFactory.init();
		//매번 동일한 객체를 리턴하기 위해 생성한객체를 searchClientFactory 필드에 보관
		this.searchClientFactory = searchClientFactory;
		return this.searchClientFactory;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return SearchClientFactory.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}
	
}
