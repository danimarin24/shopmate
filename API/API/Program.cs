using API;
using Microsoft.OpenApi.Models;
using API.Context;
using API.Controllers;
using API.Respositories.Implementations;
using API.Respositories.Interfaces;
using API.Services;
using API.Services.Implementations;
using API.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

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


// Configure the database context
builder.Services.AddDbContext<ShopMateContext>(options =>
    options.UseMySql(builder.Configuration.GetConnectionString("DefaultConnection"),
        Microsoft.EntityFrameworkCore.ServerVersion.Parse("8.0.36-mysql")));

// Register AutoMapper

builder.Services.AddAutoMapper(typeof(MappingProfile));

// Register application services
builder.Services.AddScoped<IBoardRepository, BoardRepository>();
builder.Services.AddScoped<ICardRepository, CardRepository>();
//builder.Services.AddScoped<IUserRepository, UserRepository>();
//builder.Services.AddScoped<INotificationService, NotificationService>();

builder.Services.AddScoped<IBoardService, BoardService>();
builder.Services.AddScoped<ICardService, CardService>();
builder.Services.AddScoped<IUserService, UserService>();

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
