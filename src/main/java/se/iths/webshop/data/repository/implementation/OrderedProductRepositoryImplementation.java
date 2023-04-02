package se.iths.webshop.data.repository.implementation;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import se.iths.webshop.business.entity.OrderedProduct;
import se.iths.webshop.business.entity.Product;
import se.iths.webshop.data.repository.OrderedProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class OrderedProductRepositoryImplementation implements OrderedProductRepository {
    @Override
    public Optional<Product> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findByCategory(String category) {
        return Optional.empty();
    }

    @Override
    public Optional<Product> findById(long id) {
        return Optional.empty();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends OrderedProduct> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends OrderedProduct> List<S> saveAllAndFlush(Iterable<S> entities) {
        return null;
    }

    @Override
    public void deleteAllInBatch(Iterable<OrderedProduct> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public OrderedProduct getOne(Long aLong) {
        return null;
    }

    @Override
    public OrderedProduct getById(Long aLong) {
        return null;
    }

    @Override
    public OrderedProduct getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends OrderedProduct> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends OrderedProduct> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends OrderedProduct> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends OrderedProduct> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends OrderedProduct> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends OrderedProduct> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends OrderedProduct, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends OrderedProduct> S save(S entity) {
        return null;
    }

    @Override
    public <S extends OrderedProduct> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<OrderedProduct> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<OrderedProduct> findAll() {
        return null;
    }

    @Override
    public List<OrderedProduct> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(OrderedProduct entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends OrderedProduct> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<OrderedProduct> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<OrderedProduct> findAll(Pageable pageable) {
        return null;
    }
}
