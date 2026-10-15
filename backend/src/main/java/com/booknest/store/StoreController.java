package com.booknest.store;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins={"http://localhost:5173","http://127.0.0.1:5173"})
public class StoreController {
 private final BookRepository books; private final UserRepository users; private final OrderRepository orders;
 public StoreController(BookRepository b,UserRepository u,OrderRepository o){books=b;users=u;orders=o;}

 @GetMapping("/books") public List<Book> allBooks(){
  if(books.count()==0){
   books.saveAll(List.of(
    new Book("Clean Code","Robert C. Martin","Programming",599,4.8,"https://covers.openlibrary.org/b/isbn/9780132350884-L.jpg","A practical guide to writing readable, maintainable code."),
    new Book("Python Crash Course","Eric Matthes","Programming",499,4.7,"https://covers.openlibrary.org/b/isbn/9781718502703-L.jpg","Hands-on Python projects for new developers."),
    new Book("Artificial Intelligence: A Modern Approach","Stuart Russell & Peter Norvig","AI",699,4.6,"https://covers.openlibrary.org/b/isbn/9780134610993-L.jpg","A broad introduction to modern artificial intelligence."),
    new Book("The Psychology of Money","Morgan Housel","Finance",399,4.8,"https://covers.openlibrary.org/b/isbn/9780857197689-L.jpg","Timeless lessons about money and behaviour."),
    new Book("Atomic Habits","James Clear","Self Help",449,4.9,"https://covers.openlibrary.org/b/isbn/9780735211292-L.jpg","Small habits that can lead to meaningful change."),
    new Book("The Alchemist","Paulo Coelho","Fiction",299,4.5,"https://covers.openlibrary.org/b/isbn/9780062315007-L.jpg","A fable about following your dreams."),
    new Book("Deep Learning","Ian Goodfellow, Yoshua Bengio & Aaron Courville","AI",899,4.5,"https://covers.openlibrary.org/b/isbn/9780262035613-L.jpg","Foundations of deep learning and neural networks."),
    new Book("Rich Dad Poor Dad","Robert T. Kiyosaki","Finance",349,4.4,"https://covers.openlibrary.org/b/isbn/9781612680194-L.jpg","A popular personal-finance perspective.")
   ));
  }
  return books.findAll();
 }
 @PostMapping("/auth/register") public Map<String,Object> register(@RequestBody Map<String,String> body){
  String email=body.getOrDefault("email","").trim().toLowerCase(), password=body.getOrDefault("password",""), name=body.getOrDefault("name","Reader").trim();
  if(email.isBlank()||password.length()<6) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Enter an email and password with at least 6 characters.");
  if(users.findByEmailIgnoreCase(email).isPresent()) throw new ResponseStatusException(HttpStatus.CONFLICT,"An account with this email already exists.");
  AppUser u=new AppUser();u.email=email;u.password=password;u.name=name.isBlank()?"Reader":name;users.save(u);
  return Map.of("id",u.id,"name",u.name,"email",u.email);
 }
 @PostMapping("/auth/login") public Map<String,Object> login(@RequestBody Map<String,String> body){
  String email=body.getOrDefault("email","").trim();
  AppUser u=users.findByEmailIgnoreCase(email).orElseThrow(()->new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password."));
  if(!Objects.equals(u.password,body.getOrDefault("password",""))) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password.");
  return Map.of("id",u.id,"name",u.name,"email",u.email);
 }
 @PostMapping("/orders") public StoreOrder placeOrder(@RequestBody Map<String,Object> body){
  Object rawId=body.get("userId"); if(rawId==null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Please log in before placing an order.");
  Long userId=Long.valueOf(rawId.toString());
  if(!users.existsById(userId)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Please log in again.");
  List<?> items=(List<?>)body.getOrDefault("items",List.of()); if(items.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Your cart is empty.");
  double total=items.stream().mapToDouble(x->{Map<?,?> m=(Map<?,?>)x;return Double.parseDouble(m.get("price").toString())*Integer.parseInt(m.get("quantity").toString());}).sum();
  StoreOrder order=new StoreOrder();order.userId=userId;order.itemsJson=items.stream().map(Object::toString).collect(Collectors.joining(" | "));order.total=total;
  return orders.save(order);
 }
 @GetMapping("/orders/{userId}") public List<StoreOrder> getOrders(@PathVariable Long userId){
  if(!users.existsById(userId)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Account not found.");
  return orders.findByUserIdOrderByCreatedAtDesc(userId);
 }
}
