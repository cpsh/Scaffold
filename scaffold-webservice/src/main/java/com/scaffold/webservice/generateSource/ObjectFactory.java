
package com.scaffold.webservice.generateSource;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.scaffold.webservice.generateSource package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Vote_QNAME = new QName("http://inf.server.webservice.scaffold.com/", "vote");
    private final static QName _VoteResponse_QNAME = new QName("http://inf.server.webservice.scaffold.com/", "voteResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.scaffold.webservice.generateSource
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link VoteResponse }
     * 
     */
    public VoteResponse createVoteResponse() {
        return new VoteResponse();
    }

    /**
     * Create an instance of {@link Vote }
     * 
     */
    public Vote createVote() {
        return new Vote();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Vote }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://inf.server.webservice.scaffold.com/", name = "vote")
    public JAXBElement<Vote> createVote(Vote value) {
        return new JAXBElement<Vote>(_Vote_QNAME, Vote.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link VoteResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://inf.server.webservice.scaffold.com/", name = "voteResponse")
    public JAXBElement<VoteResponse> createVoteResponse(VoteResponse value) {
        return new JAXBElement<VoteResponse>(_VoteResponse_QNAME, VoteResponse.class, null, value);
    }

}
