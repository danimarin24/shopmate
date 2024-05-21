using AutoMapper;
using API.DTOs;
using API.Model;

namespace API;

public class MappingProfile : Profile
{
    public MappingProfile()
    {
        // Define your mappings here
        CreateMap<Card, CardDto>().ReverseMap();
        CreateMap<CardDto, Card>();
        // Add other mappings as necessary
    }
}
