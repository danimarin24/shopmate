using System.Text.Json.Serialization;
using API.Authentication;
using API.Context;
using API.Controllers;
using Microsoft.OpenApi.Models;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddControllers();
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(c =>
{
    c.AddSecurityDefinition("ApiKey", new OpenApiSecurityScheme
    {
        Description = "The API Key to access the API",
        Type = SecuritySchemeType.ApiKey,
        Name = "x-api-key",
        In = ParameterLocation.Header,
        Scheme = "ApiKeyScheme"
    });
});

builder.Services.AddControllers().AddJsonOptions(x =>
    x.JsonSerializerOptions.ReferenceHandler = null);


builder.Services.AddControllers(
   options => options.SuppressImplicitRequiredAttributeForNonNullableReferenceTypes = true);
 

builder.Services.AddDbContext<ShopMateContext>();

builder.Services.AddControllers().AddApplicationPart(typeof(SharedImageController).Assembly);

var app = builder.Build();

if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

app.UseHttpsRedirection();

app.UseRouting();

app.UseAuthorization();

app.UseDefaultFiles();

app.UseStaticFiles();

app.MapControllers();

//app.UseMiddleware<ApiKeyAuthMiddleware>();

app.Run();
