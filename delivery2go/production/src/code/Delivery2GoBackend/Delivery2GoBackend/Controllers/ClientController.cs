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
	public class ClientController : ApiController
	{

		deliveryContext context;
		QueryBuilder<Client> queryBuider;

		public ClientController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<Client>();
		}

		// GET api/client/get/
		public System.Collections.ICollection Get(string filter = null, string orderby = null, int top = -1, int skip = -1)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			return queryBuider.CreateQuery(context.Clients.AsNoTracking(), filter, orderby, skip, top).ToList();
		}

		// GET api/client/get/5
		public object Get(int id)
		{
			return context.Clients.AsNoTracking()
				.Where(x=>x.Id == id)
			.FirstOrDefault();
		}

		// POST api/client/
		public HttpResponseMessage Post([FromBody]Client value)
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
			context.Entry(value).State = System.Data.EntityState.Added;
			context.SaveChanges();

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/client/
		public HttpResponseMessage Put([FromBody]Client value, string filter = null)
		{
			ServerValidationInfo vi = null;
			value.CreateDate=DateTime.Now;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (filter == null)
			{
				context.Entry(value).State = System.Data.EntityState.Modified;
			}
			else
			{
				var old = context.Clients.SingleOrDefault(queryBuider.CreateWhere(filter));
				old.Id = value.Id;
				old.Name = value.Name;
				old.Guid = value.Guid;
				old.LastName = value.LastName;
				old.LastName2 = value.LastName2;
				old.AdressNo = value.AdressNo;
				old.AddressStreet = value.AddressStreet;
				old.Email = value.Email;
				old.Phone = value.Phone;
				old.Mobile = value.Mobile;
				old.Username = value.Username;
				old.Password = value.Password;
				old.IsRegistered = value.IsRegistered;
				old.Lat = value.Lat;
				old.Lng = value.Lng;
				old.CreateDate = value.CreateDate;
				old.UpdateDate = value.UpdateDate;
				old.DeviceId = value.DeviceId;
				old.SessionId = value.SessionId;
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var result = context.SaveChanges() > 0;
			return Request.CreateResponse(HttpStatusCode.OK, result);
		}

		// DELETE api/client/
		public bool Delete(string filter = null)
		{
			IQueryable<Client> query = context.Clients;
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{
				context.Clients.Remove(item);
			}
			return context.SaveChanges() > 0;
		}

		// GET api/client/count
		[HttpGet]
		public int Count(string filter = null)
		{
			IQueryable<Client> query = context.Clients.AsNoTracking();
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			return query.Count();
		}

		// GET api/client/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.Clients.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
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
