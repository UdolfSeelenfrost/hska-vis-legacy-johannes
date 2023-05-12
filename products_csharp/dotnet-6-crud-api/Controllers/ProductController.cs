using Microsoft.AspNetCore.Authorization;
using WebApi.Entities;

namespace WebApi.Controllers;

using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Services;

[ApiController]
[AllowAnonymous]
[Route("api/products")]
public class ProductController : ControllerBase
{
    private IProductService _productService;
    private IMapper _mapper;

    public ProductController(
        IProductService productService,
        IMapper mapper)
    {
        _productService = productService;
        _mapper = mapper;
    }

    [HttpGet]
    public async Task<ActionResult> GetAll()
    {
        Console.WriteLine("Executed");
        var products = await  _productService.GetAll();
        return Ok(products);
    }

    [HttpGet("one")]
    public async Task<ActionResult<Product>> GetOne()
    {
        var product = await _productService.GetOne();
        return Ok(product);
    }
    
    [HttpGet("{id}")]
    public IActionResult GetById(int id)
    {
        var user = _productService.GetById(id);
        return Ok(user);
    }

    [HttpPost]
    public IActionResult Create([FromQuery] string name, [FromQuery] string details, [FromQuery] double price, [FromQuery] int categoryId)
    {
        _productService.Create(name, details, price, categoryId);
        return Ok(new { message = "User created" });
    }

    [HttpPut("{id}")]
    public IActionResult Update([FromRoute] int id, [FromQuery] string name, [FromQuery] string details, [FromQuery] double price, [FromQuery] int categoryId)
    {
        _productService.Update(id, name, details, price, categoryId);
        return Ok(new { message = "User updated" });
    }

    [HttpDelete("{id}")]
    public IActionResult Delete(int id)
    {
        _productService.Delete(id);
        return Ok(new { message = "User deleted" });
    }
}