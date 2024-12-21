## Spring Boot Reactor Example

This project demonstrates the use of Spring Boot along with Project Reactor to handle reactive programming. The application implements various examples of reactive streams with Flux and Mono
### Usage

##### Main Class

The main entry point is SpringBootReactorApplication.java, which executes predefined examples of Flux and Mono streams.

You can enable or disable examples by commenting/uncommenting the respective lines in the run method:

    @Override
    public void run(String... args) throws Exception {
    // exampleIterator();
    // exampleFlatMap();
    // exampleToString();
    exampleToCollectList();
    }

##### Running Examples

Each example demonstrates a specific reactive programming concept such as:

Mapping and filtering streams.

Converting Flux to Mono.

Combining streams using flatMap.
