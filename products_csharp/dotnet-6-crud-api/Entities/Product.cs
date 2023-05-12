using System.ComponentModel.DataAnnotations.Schema;

namespace WebApi.Entities;

[Table("product")]
public class Product
{
    public int Id { get; set; }
    public string Name { get; set; }
    public double Price { get; set; }
    public string Details { get; set; }
    [Column("category_id")]
    public int CategoryId { get; set; }
}