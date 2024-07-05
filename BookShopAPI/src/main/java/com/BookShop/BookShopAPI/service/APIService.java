package com.BookShop.BookShopAPI.service;

import com.BookShop.BookShopAPI.entity.*;
import com.BookShop.BookShopAPI.exception.BadRequestException;
import com.BookShop.BookShopAPI.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class APIService {

    private AuthorsRepository authorsRepository;
    private BookAuthorsRepository bookAuthorsRepository;
    private BooksRepository booksRepository;
    private GenresRepository genresRepository;
    private ReviewsRepository reviewsRepository;
    private RolesRepository rolesRepository;
    private TransactionsRepository transactionsRepository;
    private UsersRepository usersRepository;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public APIService(AuthorsRepository authorsRepository, BookAuthorsRepository bookAuthorsRepository, BooksRepository booksRepository, GenresRepository genresRepository, ReviewsRepository reviewsRepository, RolesRepository rolesRepository, TransactionsRepository transactionsRepository, UsersRepository usersRepository) {
        this.authorsRepository = authorsRepository;
        this.bookAuthorsRepository = bookAuthorsRepository;
        this.booksRepository = booksRepository;
        this.genresRepository = genresRepository;
        this.reviewsRepository = reviewsRepository;
        this.rolesRepository = rolesRepository;
        this.transactionsRepository = transactionsRepository;
        this.usersRepository = usersRepository;
    }

    // ------------------ Books ---------------------

    public Books insertBook(Books book){
        Genres genres = findGenreById(book.getGenreId());
        Books books = findBookByTitle(book.getTitle());
        return (genres != null && books == null) ? booksRepository.save(book) : null;
    }

    public List<Books> insertBooks(List<Books> books){
        return booksRepository.saveAll(books);
    }

    // orElse egzekutohet nese book.isPresent() == false
    public Books findBookById(String bookId){
        Optional<Books> book = booksRepository.findById(bookId);
        return book.orElse(null);
    }

    public Books findBookByTitle(String title){
        return booksRepository.findBookByTitle(title);
    }

    public Page<Books> findBooksByPrice(double price, Pageable pageable){
        return booksRepository.findBooksByPrice(price, pageable);
    }

    public Page<Books> findBooksGreaterThanPrice(double price, Pageable pageable){
        return booksRepository.findBooksGreaterThanPrice(price, pageable);
    }

    public Page<Books> findBooksLessThanPrice(double price, Pageable pageable){
        return booksRepository.findBooksLessThanPrice(price, pageable);
    }

    public Page<Books> findBooksByGenreId(String genreId, Pageable pageable){
        return booksRepository.findBooksByGenreId(genreId, pageable);
    }

    public List<Books> findBooksByGenreId(String genreId){
        return booksRepository.findBooksByGenreId(genreId);
    }

    public Page<Books> findBooksByLanguage(String language, Pageable pageable){
        return booksRepository.findBooksByLanguage(language, pageable);
    }

    public Page<Books> findAllBooks(Pageable pageable){
        return booksRepository.findAll(pageable);
    }
    public List<Books> findAllBooks(){
        return booksRepository.findAll();
    }

    public Books updateBook(Books books){
        return booksRepository.save(books);
    }

    public void deleteBookByTitle(String title){
        Books book = findBookByTitle(title);
        booksRepository.deleteById(book.getBookId());
    }

    public Page<Books> findBooksThatHaveStock(Pageable pageable){
        return booksRepository.findBooksThatHaveStock(pageable);
    }

//    -------------------- Genres ---------------------------

    public Genres insertGenre(Genres genre){
        return genresRepository.save(genre);
    }

    public List<Genres> insertGenres(List<Genres> genres){
        return genresRepository.saveAll(genres);
    }

    public Genres findGenreById(String genreId){
        Optional<Genres> genre = genresRepository.findById(genreId);
        return genre.orElse(null);
    }

    public Genres findGenreByName(String genreName){
        return genresRepository.findGenreByName(genreName);
    }

    public Genres updateGenre(Genres genre){
        return genresRepository.save(genre);
    }

    public void deleteGenreByName(String name){
        Genres genre = findGenreByName(name);
        genresRepository.delete(genre);
    }

    public Page<Genres> findAllGenres(Pageable pageable){
        return genresRepository.findAll(pageable);
    }


//    ---------------- Authors ------------------

    public Authors insertAuthor(Authors author){
        Authors authorExists = findAuthorByAuthorEmail(author.getAuthorEmail());
        return authorExists == null ? authorsRepository.save(author) : null;
    }

    public List<Authors> insertAuthors(List<Authors> authors){
        return authorsRepository.saveAll(authors);
    }

    public Authors findAuthorById(String authorId){
        Optional<Authors> author = authorsRepository.findById(authorId);
        return author.orElse(null);
    }

    public Page<Authors> findAuthorByFirstNameAndLastName(String firstName, String lastName, Pageable pageable){
        return authorsRepository.findAuthorByFirstNameAndLastName(firstName, lastName, pageable);
    }

    public Authors findAuthorByAuthorEmail(String authorEmail){
        return authorsRepository.findAuthorByAuthorEmail(authorEmail);
    }

    public List<Authors> findAuthorByNationality(String nationality){
        return authorsRepository.findAuthorByNationality(nationality);
    }

    public Authors updateAuthor(Authors author){
        return authorsRepository.save(author);
    }

    public void deleteAuthorByEmail(String authorEmail){
        Authors author = findAuthorByAuthorEmail(authorEmail);
        authorsRepository.delete(author);
    }

    public Page<Authors> findAllAuthors(Pageable pageable){
        return authorsRepository.findAll(pageable);
    }

    public List<Authors> findAllAuthors(){
        return authorsRepository.findAll();
    }

    // ----------- BookAuthors -------------


    public BookAuthors insertBookAuthor(BookAuthors bookAuthors){
        Authors author = findAuthorById(bookAuthors.getAuthorId());
        Books book = findBookById(bookAuthors.getBookId());

        if (author != null && book != null){
            BookAuthors bookAuthorsExists = findBookAuthorByAuthorIdAndBookId(bookAuthors.getAuthorId(), bookAuthors.getBookId());
            return bookAuthorsExists == null ? bookAuthorsRepository.save(bookAuthors) : null;
        }
        throw new BadRequestException("Couldn't insert BookAuthor");
    }

    public List<BookAuthors> insertBookAuthors(List<BookAuthors> bookAuthors){
        return bookAuthorsRepository.saveAll(bookAuthors);
    }

    public BookAuthors findBookAuthorByAuthorIdAndBookId(String authorId, String bookId){
        return bookAuthorsRepository.findBookAuthorByAuthorIdAndBookId(authorId, bookId);
    }

    public Page<BookAuthors> findBookAuthorsByAuthorId(String authorId, Pageable pageable){
        return bookAuthorsRepository.findBookAuthorsByAuthorId(authorId, pageable);
    }

    public List<BookAuthors> findBookAuthorsByAuthorId(String authorId){
        return bookAuthorsRepository.findBookAuthorsByAuthorId(authorId);
    }

    public Page<BookAuthors> findBookAuthorsByBookId(String bookId, Pageable pageable){
        return bookAuthorsRepository.findBookAuthorsByBookId(bookId, pageable);
    }

    public BookAuthors updateBookAuthor(BookAuthors bookAuthors){
        return bookAuthorsRepository.save(bookAuthors);
    }

    public List<BookAuthors> findAllBookAuthors() {
        return bookAuthorsRepository.findAll();
    }

    public List<BookAuthors> findBookAuthorsByAuthorEmail(String authorEmail){
        List<BookAuthors> allBookAuthors = findAllBookAuthors();
        List<BookAuthors> bookAuthorsWithAuthorEmail = new ArrayList<>();
        Authors author = findAuthorByAuthorEmail(authorEmail);

        if (author != null && allBookAuthors != null){
            allBookAuthors.forEach(bookAuthor -> {
                if (bookAuthor.getAuthorId().equals(author.getAuthorId()))
                    bookAuthorsWithAuthorEmail.add(bookAuthor);
            });
        }

        return bookAuthorsWithAuthorEmail;
    }

    public List<BookAuthors> findBookAuthorsByBookTitle(String title) {
        List<BookAuthors> allBookAuthors = findAllBookAuthors();
        List<BookAuthors> bookAuthorsWithBookTitle = new ArrayList<>();
        Books book = findBookByTitle(title);

        if (book != null && allBookAuthors != null){
            allBookAuthors.forEach(bookAuthor -> {
                Books currentBook = findBookById(bookAuthor.getBookId());
                if (currentBook.getTitle().equals(book.getTitle()))
                    bookAuthorsWithBookTitle.add(bookAuthor);
            });
        }

        return bookAuthorsWithBookTitle;
    }

    public BookAuthors findBookAuthorByBookAuthorId(String bookAuthorId) {
        Optional<BookAuthors> bookAuthor = bookAuthorsRepository.findById(bookAuthorId);
        return bookAuthor.orElse(null);
    }

    public void deleteBookAuthorById(String bookAuthorId) {
        bookAuthorsRepository.deleteById(bookAuthorId);
    }

//    ----------- Roles ------------

    public Roles findRoleById(String roleId){
        Optional<Roles> role = rolesRepository.findById(roleId);
        return role.orElse(null);
    }

    public Page<Roles> findAllRoles(Pageable pageable){
        return rolesRepository.findAll(pageable);
    }

    public List<Roles> findAllRoles(){
        return rolesRepository.findAll();
    }

    public Roles findRoleByName(String roleName){
        return rolesRepository.findRoleByName(roleName).orElse(null);
    }

    public Roles insertRole(Roles role){
        Roles roleExists = findRoleByName(role.getRoleName().name());
        return roleExists == null ? rolesRepository.save(role) : null;
    }

    public List<Roles> insertRoles(List<Roles> roles){
        return rolesRepository.saveAll(roles);
    }

    public Roles updateRole(Roles role){
        return rolesRepository.save(role);
    }

    public void deleteRoleById(String roleId){
        rolesRepository.deleteById(roleId);
    }

    public void deleteRoleByName(String name){
        Roles roles = findRoleByName(name);
        rolesRepository.delete(roles);
    }

//    -------------- Users ---------------

    public Users findUserByEmail(String email) {
        return usersRepository.findUserByEmail(email);
    }
    public Users findUserById(String userId) {
        return usersRepository.findById(userId)
                .orElse(null);
    }

    public Users insertUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    public List<Users> findAllUsers() {
        return usersRepository.findAll();
    }

    public Page<Users> findAllUsers(Pageable pageable) {
        return usersRepository.findAll(pageable);
    }

    public Users updateUser(Users user) {
        return usersRepository.save(user);
    }

    public void deleteUserByEmail(String email) {
        Users user = findUserByEmail(email);
        if (user != null) usersRepository.delete(user);
    }

//    -------------- Transactions ---------------

    public Transactions insertTransaction(Transactions transaction){
        Books book = findBookById(transaction.getBookID());
        Users user = findUserById(transaction.getUserID());

        if (book != null && user != null && book.getStock() >= 0 && transaction.getStock() >= 0 &&
                book.getStock() - transaction.getStock() >= 0) {
            transaction.setTotalPrice(transaction.getStock() * book.getPrice());
            book.setStock(book.getStock() - transaction.getStock());
            updateBook(book);
            return transactionsRepository.save(transaction);
        }else
            return null;
    }

    public Transactions findTransactionById(String transactionId){
        return transactionsRepository.findById(transactionId)
                .orElse(null);
    }

    public Page<Transactions> findTransactionsByUserId(String userId, Pageable pageable){
        return transactionsRepository.findTransactionsByUserId(userId, pageable);
    }

    public List<Transactions> findTransactionsByUserId(String userId){
        return transactionsRepository.findTransactionsByUserId(userId);
    }

    public Page<Transactions> findTransactionsByBookId(String bookId, Pageable pageable){
        return transactionsRepository.findTransactionsByBookId(bookId, pageable);
    }

    public List<Transactions> findTransactionsByBookId(String bookId){
        return transactionsRepository.findTransactionsByBookId(bookId);
    }

    public Page<Transactions> findTransactionsByUserIdAndBookId(String userdId, String bookId, Pageable pageable){
        return transactionsRepository.findTransactionsByUserIdAndBookId(userdId, bookId, pageable);
    }

    public Page<Transactions> findTransactionsByTotalPrice(double totalPrice, Pageable pageable){
        return transactionsRepository.findTransactionsByTotalPrice(totalPrice, pageable);
    }
    public Page<Transactions> findTransactionByDate(LocalDate date, Pageable pageable){
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay().minusNanos(1);
        return transactionsRepository.findByDate(startOfDay, endOfDay, pageable);
    }

    public Page<Transactions> findTransactionByMonth(int month, Pageable pageable){
        return transactionsRepository.findByMonth(month, pageable);
    }

    public Page<Transactions> findTransactionByYear(int year, Pageable pageable){
        return transactionsRepository.findByYear(year, pageable);
    }

    public void deleteTransaction(Transactions transaction){
        transactionsRepository.delete(transaction);
    }

//    -------------- Reviews ---------------

    public Reviews insertReview(Reviews review){
        Books book = findBookById(review.getBookId());
        Users user = findUserById(review.getUserId());

        if (book != null && user != null && review.getRating() <= 5.0 && review.getRating() >= 0.0) {
            if (!findTransactionsByUserId(user.getUserId()).isEmpty() && !findTransactionsByBookId(book.getBookId()).isEmpty()
                     && reviewsRepository.findReviewByUserIdAndBookId(user.getUserId(), book.getBookId()) == null)
                return reviewsRepository.save(review);
        }
        return null;
    }

    public Page<Reviews> findReviewsByBookId(String bookId, Pageable pageable){
        return reviewsRepository.findReviewsByBookId(bookId, pageable);
    }

    public Page<Reviews> findReviewsByUserId(String userId, Pageable pageable){
        return reviewsRepository.findReviewsByUserId(userId, pageable);
    }

    public List<Reviews> findReviewsByUserId(String userId){
        return reviewsRepository.findReviewsByUserId(userId);
    }

    public Page<Reviews> findReviewsByRating(double rating, Pageable pageable){
        return reviewsRepository.findReviewsByRating(rating, pageable);
    }

    public Reviews findReviewByUserIdAndBookId(String userId, String bookId){
        return reviewsRepository.findReviewByUserIdAndBookId(userId, bookId);
    }

    public void deleteReviewByUserIdAndBookId(String userId, String bookId){
        Reviews reviews = reviewsRepository.findReviewByUserIdAndBookId(userId, bookId);
        if (reviews != null) reviewsRepository.delete(reviews);
    }
}



























