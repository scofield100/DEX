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
	public class EntityImagesImageController : ApiController
	{

		deliveryContext context;
		QueryBuilder<EntityImages> queryBuider;
		QueryBuilder<Image> distintQueryBuider;

		public EntityImagesImageController()
		{
			context = new deliveryContext();
			queryBuider = new QueryBuilder<EntityImages>()
				.MapMember("Id", x => x.Image.Id)
				.MapMember("Location", x => x.Image.Location)
				.MapMember("UpdateUserId", x => x.Image.UpdateUserId)
				.MapMember("UpdateDate", x => x.Image.UpdateDate)
				.MapMember("UpdateUserName", x => x.Image.UpdateUser.Name);

			distintQueryBuider = new QueryBuilder<Image>()
				.MapMember("UpdateUserName", x => x.UpdateUser.Name);
		}

		// GET api/entityimagesimage/get/
		public System.Collections.ICollection Get(int EntityId, string filter = null, string orderby = null, int top = -1, int skip = -1, bool distint=false)
		{
			if (orderby == null)
			{
				orderby = "Id";
			}
			if(!distint)
			{
				return queryBuider.CreateQuery(context.EntityImageses.AsNoTracking().Where(x=>x.EntityId == EntityId), filter, orderby, skip, top, x => new
					{
						Id=x.Image.Id,
						Location=x.Image.Location,
						UpdateUserId=x.Image.UpdateUserId,
						UpdateUserName=x.Image.UpdateUser.Name,
						UpdateDate=x.Image.UpdateDate,
				}).ToList();
			}
			else
			{
				return distintQueryBuider.CreateQuery(context.Images.AsNoTracking().Where(x => !x.EntityImageses.Any(y => y.EntityId == EntityId)), filter, orderby, skip, top, x => new
					{
						Id=x.Id,
						Location=x.Location,
						UpdateUserId=x.UpdateUserId,
						UpdateUserName=x.UpdateUser.Name,
						UpdateDate=x.UpdateDate,
				}).ToList();
			}
		}

		// GET api/EntityImagesImage/get/5
		public object Get(int id, int EntityId)
		{
			return context.EntityImageses.AsNoTracking().Where(x=>x.EntityId == EntityId && x.Image.Id == id)
				.Select(x=> new
				{
					Id=x.Image.Id,
					Location=x.Image.Location,
					UpdateUserId=x.Image.UpdateUserId,
					UpdateUserName=x.Image.UpdateUser.Name,
					UpdateDate=x.Image.UpdateDate,
				})
			.FirstOrDefault();
		}

		// POST api/entityimages/
		public HttpResponseMessage Post(int EntityId, [FromBody]Image value)
		{
			ServerValidationInfo vi = null;
			value.UpdateDate=DateTime.Now;
			if (!ModelState.IsValid)
			{
				vi = new ServerValidationInfo(ModelState);
			}
			if (vi != null && vi.ContainsError)
				return Request.CreateResponse(HttpStatusCode.BadRequest, vi);
			var rel = new EntityImages(){ EntityId=EntityId, Image=value };
			context.Entry(rel).State = System.Data.EntityState.Added;
			context.SaveChanges();
			value.EntityImageses= null;

			return Request.CreateResponse(HttpStatusCode.OK, value);
		}

		// PUT api/entityimagesimage/
		public HttpResponseMessage Put([FromBody]Image value)
		{
			ServerValidationInfo vi = null;
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

		// DELETE api/entityimagesimage/
		public bool Delete(int EntityId, string filter = null)
		{
			IQueryable<EntityImages> query = context.EntityImageses
                .Include("Image")
                .Where(x=>x.EntityId == EntityId);
			if (filter != null)
			{
				query = query.Where(queryBuider.CreateWhere(filter));
			}
			foreach (var item in query)
			{				
                context.Images.Remove(item.Image);
			}            

			return context.SaveChanges() > 0;
		}

		// GET api/entityimagesimage/count
		[HttpGet]
		public int Count(int EntityId,string filter = null, bool distint=false)
		{
			if(!distint)
			{
				var query = context.EntityImageses.AsNoTracking().Where(x=>x.EntityId == EntityId);
				if (filter != null)
				{
					query = query.Where(queryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
			else
			{
				var query = context.Images.AsNoTracking().Where(x => !x.EntityImageses.Any(y => y.EntityId == EntityId));
				if (filter != null)
				{
					query = query.Where(distintQueryBuider.CreateWhere(filter));
				}
				return query.Count();
			}
		}

		// GET api/entityimages/find
		[HttpGet]
		public object Find(string filter)
		{
			return context.EntityImageses.AsNoTracking()
				.Where(queryBuider.CreateWhere(filter))
				.Select(x=> new
				{
					Id=x.Image.Id,
					Location=x.Image.Location,
					UpdateUserId=x.Image.UpdateUserId,
					UpdateUserName=x.Image.UpdateUser.Name,
					UpdateDate=x.Image.UpdateDate,
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
