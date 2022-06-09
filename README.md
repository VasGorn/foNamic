# foNamic 
#### **F**ractional **O**rder Dy**Namic**
#### _App for Approximation of Dynamic Objects by Fractional Order Transfer Function_

At the beginning of work with the program, the user must provide data on the [transient process][transient-response] in response to the step function. Thіs data can be obtained, for example, from experiments and must be loaded as a csv file. Also, the step time size of provided data and input signal level must be specified in the form on page.  
After clicking on the "NEXT" button, first of all, the type of [transfer function][transfer-function] with a [fractional order][fractional-order] must be selected. Secondly, user specify the range of coefficients, according to which the [approximation][approximation] will take place.  
Next, the [genetic algorithm][genetic-algorithm] is configured: the population size, the number of elites, the type of selection, and the probability of gene mutation must be set by user.  
When all settings are specified, user can start the process of optimizing the transfer function parameters using a genetic algorithm. The figure will show the process of evolution of the fittest individual. At the output, we get the parameters with the best approximation index.  

The project itself is built without frameworks - the controller function is performed by **Servlet** that runs in the **Tomcat** servlet container.  
Web pages are created using **JSP** (JavaServer Pages). Some basic logic on the pages is done by **JavaScript**. **Boostrap CSS** is used for global style. Cdisplaying graphs is done through the library **Chart.js**.  

![Input data](images/input_data.png)
![Genetic algorithm](images/ga.png)

##### TODO:
- break the Сontroller class into simpler ones according to the principle of Single Responsibility;
- displaying the five best individuals in the table after the end of the approximation;
- give several users access to the program (login);
- choice between different types of selection and crossover;
- fix elitism (to be able to set more than one elite);
- save approximation data in the database;
- upgrade the user interface;
- perform multithread calculations;
- rework the project - a big part of code is a procedural style of programming.

[transient-response]: <https://en.wikipedia.org/wiki/Transient_response>
[transfer-function]:<https://en.wikipedia.org/wiki/Transfer_function>
[fractional-order]:<https://en.wikipedia.org/wiki/Fractional-order_system>
[genetic-algorithm]:<https://en.wikipedia.org/wiki/Genetic_algorithm>
[approximation]:<https://en.wikipedia.org/wiki/Approximation>
