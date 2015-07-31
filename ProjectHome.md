Treaty is a contract framework for dynamic component models. It supports the precise specification of relationships between collaborating components and verification with respect to these contracts. To explain this, consider a simple clock application in Eclipse (this application can be installed from the following update site: http://treaty.googlecode.com/svn/tags/release1.2.1/treaty-eclipse-updatesite/site.xml). The clock view provided by the plugin displays date and time, but the actual date formatting services are provided by additional bundles. The relationship between these bundles is described by precise contracts that reference types defined in an ontology. The following contract types are used in the example:

  * Java classes must implement Java interfaces (level 1 contracts) - used to provide date formatter classes implementing a given interface. This is a classical _syntactic_ contract.
  * XML instances must instantiate a given XMLSchema - used to provide date format strings, contracts are therefore _polymorphic_.
  * Java classes have to pass JUnit functional tests (level 2 contracts) - used to make sure that data formatter implementation classes render at least date, month and the last two digits of the year. This is an example of a _semantic_ contract.
  * Java classes have to pass JUnit performance tests (level 4 contracts) - used to make sure dates can be rendered in <10ms - JUnit is used as well here. This is an example of a  _quality of service_ contract.
  * complex contracts: a plugin can either provide the class or the XML format definition, but not both (exclusive disjunction).


![http://www-ist.massey.ac.nz/jbdietrich/treaty/screenshots/contractviz1.jpg](http://www-ist.massey.ac.nz/jbdietrich/treaty/screenshots/contractviz1.jpg)

The prototype uses a verification service to check the system for integrity as defined by these contracts. This is useful in complex dynamic systems that change often as new services (components) are discovered and integrated. Treaty is **non invasive**: contracts are just text files added to the plugin meta data ([example](http://treaty.googlecode.com/svn/trunk/treaty-eclipse-example/treaty-eclipse-example-clock/META-INF/net.java.treaty.eclipse.example.clock.dateformatter.contract)). Contracts can either be attached to consumer components, or to special legislator components.
Treaty is **open**: the contract vocabulary consisting of types and relationships has an open modular design. In particular, the underlying component model itself can be used to make contributions to the contract vocabulary.
Treaty is **dynamic**: contracts can reference participating components using variables. Component resources are referenced using complex terms that are resolved when binding occurs (= when components start to collaborate).

The Treaty proof of concept implementation for Eclipse can be installed as plugin from the following update sites:

Treaty 1 (stable - recommended):
http://treaty.googlecode.com/svn/tags/release1.2.1/treaty-eclipse-updatesite/site.xml.

Treaty 2 (experimental, with support for contract triggers and contract injection):
http://treaty.googlecode.com/svn/tags/release2.1.1/treaty-eclipse-updatesite/site.xml.

## Related Publications ##

  * Jens Dietrich, Graham Jenson: Components, Contracts and Vocabularies - Making Dynamic Component Assemblies more Predictable. In [JOT, vol. 8, no. 7, November - December 2009](http://www.jot.fm/issues/issue_2009_11/article4.pdf).
  * Jens Dietrich, Lucia Stewart: Component Contracts in Eclipse - A Case Study. Proceedings [CBSE2010](http://dx.doi.org/10.1007/978-3-642-13238-4_9), 2010.
  * Jens Dietrich: An Ontological Model for Component Collaboration. Journal of Research and Practice in Information Technology (JRPIT), Vol. 43, No1, 2011.
  * Antoine Beugnard, Jean-Marc Jézéquel, Noël Plouzeau, Damien Watkins, "Making Components Contract Aware," Computer ,vol. 32, no. 7,  pp. 38-45, July, 1999. [DOI](http://doi.ieeecomputersociety.org/10.1109/2.774917) - contract levels are defined here.



## Miscellaneous ##

Treaty for Eclipse contains a custom JUnit  that allows constructor dependency injection. See also the [discussion](http://tech.groups.yahoo.com/group/junit/msearch?date=any&AM=contains&AT=&SM=contains&ST=testing+interfaces+only+and+dependecy+injection+in+test+cases&MM=contains&MT=&charset=UTF-8) on the JUnit mailing list about unit testing and dependency injection.



