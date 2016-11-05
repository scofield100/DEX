using System;
using System.Collections.Generic;
using System.Linq;
using System.Linq.Expressions;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using Enterlib;
using Enterlib.Parsing;
using Enterlib.Validation;
using Delivery2GoBackend.Models;

namespace Delivery2GoBackend.Controllers
{
	public class ClientEntityFavoritesClientController : ApiController
	{

		deliveryContext context;
		QueryBuilder<ClientEntityFavorites> queryBuider;
		QueryBuilder<Client> distintQueryBuider;

		public ClientEntityFavoritesClientController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<ClientEntityFavorites>()
				.MapMember("Id", x => x.Client.Id)
				.MapMember("Name", x => x.Client.Name)
				.MapMember("Guid", x => x.Client.Guid)
				.MapMember("LastName", x => x.Client.LastName)
				.MapMember("LastName2", x => x.Client.LastName2)
				.MapMember("AdressNo", x => x.Client.AdressNo)
				.MapMember("AddressStreet", x => x.Client.AddressStreet)
				.MapMember("Email", x => x.Client.Email)
				.MapMember("Phone", x => x.Client.Phone)
				.MapMember("Mobile", x => x.Client.Mobile)
				.MapMember("Username", x => x.Client.Username)
				.MapMember("Password", x => x.Client.Password)
				.MapMember("IsRegistered", x => x.Client.IsRegistered)
				.MapMember("Lat", x => x.Client.Lat)
				.MapMember("Lng", x => x.Client.Lng)
				.MapMember("CreateDate", x => x.Client.CreateDate)
				.MapMember("UpdateDate", x => x.Client.UpdateDate)
				.MapMember("DeviceId", x => x.Client.DeviceId)
				.MapMember("SessionId", x => x.Client.SessionId);

			distintQueryBuider = new QueryBuilder<Client>()
;
		}

		// GET api/cliententityfavoritesclient/get/
		public System.Collections.ICollection Get(int EntityId, string filter = null, string orderby = null, int top = -1, int skip = -1, bool distint=false)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			if(!distint)
			{
				return queryBuider.CreateQuery(context.ClientEntityFavoriteses.AsNoTracking().Where(x=>x.EntityId == EntityId), filter, orderby, skip, top, x => new
					{
						Id=x.Client.Id,
						Name=x.Client.Name,
						Guid=x.Client.Guid,
						LastName=x.Client.LastName,
						LastName2=x.Client.LastName2,
						AdressNo=x.Client.AdressNo,
						AddressStreet=x.Client.AddressStreet,
						Email=x.Client.Email,
						Phone=x.Client.Phone,
						Mobile=x.Client.Mobile,
						Username=x.Client.Username,
						Password=x.Client.Password,
						IsRegistered=x.Client.IsRegistered,
						Lat=x.Client.Lat,
						Lng=x.Client.Lng,
						CreateDate=x.Client.CreateDate,
						UpdateDate=x.Client.UpdateDate,
						DeviceId=x.Client.DeviceId,
						SessionId=x.Client.SessionId,
				}).ToList();
			}
			else
			{
				return distintQueryBuider.CreateQuery(context.Clients.AsNoTracking().Where(x => !x.ClientEntityFavoriteses.Any(y => y.EntityId == EntityId)), filter, orderby, skip, top, x => new
					{
						Id=x.Id,
						Name=x.Name,
						Guid=x.Guid,
						LastName=x.LastName,
						LastName2=x.LastName2,
						AdressNo=x.AdressNo,
						AddressStreet=x.AddressStreet,
						Email=x.Email,
						Phone=x.Phone,
						Mobile=x.Mobile,
						Username=x.Username,
						Password=x.Password,
						IsRegistered=x.IsRegistered,
						Lat=x.Lat,
						Lng=x.Lng,
						CreateDate=x.CreateDate,
						UpdateDate=x.UpdateDate,
						DeviceId=x.DeviceId,
						SessionId=x.SessionId,
				}).ToList();
			}
		}

		// GET api/ClientEntityFavoritesClient/get/5
		public object Get(int id, int EntityId)
		{
			return context.ClientEntityFavoriteses.AsNoTracking().Where(x=>x.EntityId == EntityId && x.Client.Id == id)
				.Select(x=> new
				{
					Id=x.Client.Id,
					Name=x.Client.Name,
					Guid=x.Client.Guid,
					LastName=x.Client.LastName,
					LastName2=x.Client.LastName2,
					AdressNo=x.Client.AdressNo,
					AddressStreet=x.Client.AddressStreet,
					Email=x.Client.Email,
					Phone=x.Client.Phone,
					Mobile=x.Client.Mobile,
					Username=x.Client.Username,
					Password=x.Client.Password,
					IsRegistered=x.Client.IsRegistered,
					Lat=x.Client.Lat,
					Lng=x.Client.Lng,
					CreateDate=x.Client.CreateDate,
					UpdateDate=x.Client.UpdateDate,
					DeviceId=x.Client.DeviceId,
					SessionId=x.Client.SessionId,
				})
			.FirstOrDefault();
		}

		// POST api/cliententityfavorites/
		public HttpResponseMessage Post(int EntityId, [FromBody]Client value)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var rel = new ClientEntityFavorites(){ EntityId=EntityId, Client=value };
			context.Entry(rel).State = System.Data.EntityState.Added;
			context.SaveChanges();
			value.ClientEntityFavoriteses= null;

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/cliententityfavoritesclient/
		public HttpResponseMessage Put([FromBody]Client value)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			context.Entry(value).State = System.Data.EntityState.Modified;
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/cliententityfavoritesclient/
		public bool Delete(int EntityId, string filter = null)
		{
			IQueryable<ClientEntityFavorites> query = context.ClientEntityFavoriteses.Where(x=>x.EntityId == EntityId);
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.ClientEntityFavoriteses.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/cliententityfavoritesclient/count
		[HttpGet]
		public int Count(int EntityId,string filter = null, bool distint=false)
		{
			if(!distint)
			{
				var query = context.ClientEntityFavoriteses.AsNoTracking().Where(x=>x.EntityId == EntityId);
				if (filter != null)
				{
					query = query.Where(queryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
			else
			{
				var query = context.Clients.AsNoTracking().Where(x => !x.ClientEntityFavoriteses.Any(y => y.EntityId == EntityId));
				if (filter != null)
				{
					query = query.Where(distintQueryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
		}

		// GET api/cliententityfavorites/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.ClientEntityFavoriteses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Client.Id,
					Name=x.Client.Name,
					Guid=x.Client.Guid,
					LastName=x.Client.LastName,
					LastName2=x.Client.LastName2,
					AdressNo=x.Client.AdressNo,
					AddressStreet=x.Client.AddressStreet,
					Email=x.Client.Email,
					Phone=x.Client.Phone,
					Mobile=x.Client.Mobile,
					Username=x.Client.Username,
					Password=x.Client.Password,
					IsRegistered=x.Client.IsRegistered,
					Lat=x.Client.Lat,
					Lng=x.Client.Lng,
					CreateDate=x.Client.CreateDate,
					UpdateDate=x.Client.UpdateDate,
					DeviceId=x.Client.DeviceId,
					SessionId=x.Client.SessionId,
				})
			.FirstOrDefault();
		}

		protected override void Dispose(bool disposing)
		{
			if (disposing)
				context.Dispose();
			base.Dispose(disposing);
		}


	}
}
