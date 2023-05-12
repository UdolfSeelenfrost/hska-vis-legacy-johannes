using Microsoft.EntityFrameworkCore;

namespace WebApi.Services;

using AutoMapper;
using BCrypt.Net;
using Entities;
using Helpers;

public interface IProductService
{
    Task<List<Product>> GetAll();
    Task<Product> GetOne();
    Product GetById(int id);
    void Create(string name, string details, double price, int categoryId);
    void Update(int id, string name, string details, double price, int categoryId);
    void Delete(int id);
}

public class ProductService : IProductService
{
    private DataContext _context;
    private readonly IMapper _mapper;

    public ProductService(
        DataContext context,
        IMapper mapper)
    {
        _context = context;
        _mapper = mapper;
    }

    public async Task<List<Product>> GetAll()
    {
        return await _context.Product.ToListAsync();
    }
    
    public async Task<Product> GetOne()
    {
        var product = await _context.Product.SingleOrDefaultAsync();
        return product;
    }

    public Product GetById(int id)
    {
        return getProduct(id);
    }

    public void Create(string name, string details, double price, int categoryId)
    {
        _context.Product.Add(new Product());
        _context.SaveChanges();
    }

    public void Update(int id, string name, string details, double price, int categoryId)
    {
        var user = getProduct(id);
        _context.Product.Update(user);
        _context.SaveChanges();
    }

    public void Delete(int id)
    {
        var user = getProduct(id);
        _context.Product.Remove(user);
        _context.SaveChanges();
    }

    // helper methods

    private Product getProduct(int id)
    {
        var user = _context.Product.Find(id);
        if (user == null) throw new KeyNotFoundException("User not found");
        return user;
    }
}