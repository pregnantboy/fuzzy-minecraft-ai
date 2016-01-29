//
//  RuleCondition.h
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 26/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface RuleCondition : NSObject <NSCoding>

- (instancetype) init;
- (instancetype) initWithCond:(NSString *)cond;
- (instancetype) initWithCond:(NSString *)cond value:(NSString *)valCode;
- (void)setCondValue: (NSString *)value;
- (BOOL)isSet;
- (void)setCondCode:(NSString *)cond;
- (NSString *)cond;
- (NSString *)value;
- (NSArray *)getPossibleValues;
+ (NSArray *)getPossibleConditions;
+ (NSArray *)getPossibleValueForCond:(NSString *)cond;
+ (NSDictionary *)conditions;
- (void)encodeWithCoder:(NSCoder *)aCoder;

@end
