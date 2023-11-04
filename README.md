# Exercise

Implement logical expressionModel evaluator.

## Specification
Your application should expose two HTTP endpoints:

### API Definition: 

```
/expressionModel
```

### API Input:

This API endpoint should take name of the logical expressionModel and its value:

### API Response:

For each request executed against the API endpoint you should return an unique identifier that represents the identifier of logical expressionModel.

### Workflow:

When this API is being called new logical expressionModel should be created and identifier of newly created logical expressionModel is returned.

### Example:

```
{
  "name": "Simple logical expressionModel",
  "value": "(customer.firstName == "JOHN" && customer.salary < 100) OR (customer.address != null && customer.address.city == "Washington")"
}
```

```
{
  "name": "Simple logical expressionModel 2",
  "value": "true == (false || true)"
}
```

### API Definition: 

```
/evaluate
```

### API Input:

This API endpoint takes expressionModel ID and JSON object as input.

### API Output:

Returns the result of evaluationModel by using the requested expressionModel and provided JSON object.

### Workflow:

When this API is being called requested logical expressionModel should be evaluated using the provied JSON object.

### Example:

```
{
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "customer":
  {
    "firstName": "JOHN",
    "lastName": "DOE", 
    "address":
    {
      "city": "Chicago",
      "zipCode": 1234, 
      "street": "56th", 
      "houseNumber": 2345
    },
    "salary": 99,
    "type": "BUSINESS"
  }
}
```

## Additional Information
You should use following frameworks for your work.

### Spring JPA
H2 database running in memory (data will not be persistent across application restarts). 

### 3rd party libraries
You are free to add/change any libraries which you might need to solve this exercise, except using any 3rd party expressionModel evaluationModel library (i.e. SpEL, JSONPath or any other). Also the requirement is that we do not have to setup / install any external software to run this application.

### Running the exercise with maven
```mvn spring-boot:run```

### Commiting
You will provide your solution by sending us a link to your repo which contains the solution for this exercise.
